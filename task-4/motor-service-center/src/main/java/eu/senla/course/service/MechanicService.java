package eu.senla.course.service;

import eu.senla.course.api.repository.IMechanicRepository;
import eu.senla.course.api.service.IGarageService;
import eu.senla.course.api.service.IMechanicService;
import eu.senla.course.api.service.IOrderService;
import eu.senla.course.dto.garage.GarageDto;
import eu.senla.course.dto.mechanic.MechanicDto;
import eu.senla.course.dto.mechanic.MechanicShortDto;
import eu.senla.course.dto.order.OrderDto;
import eu.senla.course.dto.order.OrderShortDto;
import eu.senla.course.entity.Garage;
import eu.senla.course.entity.Mechanic;
import eu.senla.course.enums.CsvMechanicHeader;
import eu.senla.course.exception.RepositoryException;
import eu.senla.course.exception.ServiceException;
import eu.senla.course.util.CsvReader;
import eu.senla.course.util.CsvWriter;
import eu.senla.course.util.exception.CsvException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Component("mechanicService")
public class MechanicService implements IMechanicService {
    private final static Logger logger = LogManager.getLogger(MechanicService.class);
    @Value("${mechanic}")
    private String mechanicPath;

    private IMechanicRepository mechanicRepository;
    private IGarageService garageService;
    private IOrderService orderService;

    public Mechanic mechanicDtoToEntity(MechanicDto mechanicDto) {
        Mechanic mechanic = new Mechanic();
        mechanic.setId(mechanicDto.getId());
        mechanic.setName(mechanicDto.getName());
        if (mechanicDto.getGarageShortDto() != null) {
            Garage garage = garageService.garageDtoToEntity(mechanicDto.getGarageShortDto());
            mechanic.setGarage(garage);
        }
        return mechanic;
    }

    public Mechanic mechanicShortDtoToEntity(MechanicShortDto mechanicDto) {
        Mechanic mechanic = new Mechanic();
        mechanic.setId(mechanicDto.getId());
        mechanic.setName(mechanicDto.getName());
        return mechanic;
    }
    @Autowired
    @Qualifier("mechanicHibernateRepository")
    public void setMechanicRepository(IMechanicRepository mechanicRepository) {
        this.mechanicRepository = mechanicRepository;
    }

    @Autowired
    @Qualifier("garageService")
    public void setGarageService(IGarageService garageService) {
        this.garageService = garageService;
    }

    @Autowired
    @Qualifier("orderService")
    public void setOrderService(IOrderService orderService) {
        this.orderService = orderService;
    }

    @Transactional
    public void addMechanic(MechanicDto mechanicDto) throws ServiceException {
        try {
            mechanicRepository.add(mechanicDtoToEntity(mechanicDto));
        } catch (RepositoryException e) {
            throw new ServiceException("RepositoryException " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<MechanicDto> getMechanics() {
        return mechanicRepository
               .getAll()
               .stream()
               .map(MechanicDto::new)
               .collect(Collectors.toList());
    }

    @Transactional
    public void setMechanics(List<MechanicDto> mechanicDtoList) {
        mechanicRepository.setAll(mechanicDtoList.stream().map(this::mechanicDtoToEntity).collect(Collectors.toList()));
    }

    @Transactional
    public void deleteMechanic(int id) {
        mechanicRepository.delete(id);
    }

    @Transactional(readOnly = true)
    public MechanicDto getMechanicById(int id) {
        return new MechanicDto(mechanicRepository.getById(id));
    }

    @Transactional
    public void updateMechanic(MechanicDto mechanicDto) throws ServiceException {
        try {
            mechanicRepository.update(mechanicDtoToEntity(mechanicDto));
        } catch (RepositoryException e) {
            throw new ServiceException("RepositoryException " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public MechanicDto firstFreeMechanic() throws ServiceException {
        if (mechanicRepository.getAll().size() == 0) {
            throw new ServiceException("Auto mechanics are not exist");
        }
        for (Mechanic mechanic: mechanicRepository.getAll()) {
            if (mechanic.isMechanicFree()) {
                return new MechanicDto(mechanic);
            }
        }
        System.out.println("Free mechanic is not found");
        return null;
    }

    @Transactional(readOnly = true)
    public void sortMechanicsBy(Comparator<MechanicDto> comparator) throws ServiceException {
        if (mechanicRepository.getAll().size() == 0) {
            throw new ServiceException("Auto mechanics are not exist");
        }
        List<MechanicDto> mechanicDtoList = mechanicRepository.getAll().stream().map(MechanicDto::new).sorted(comparator).collect(Collectors.toList());
        for (MechanicDto mechanic: mechanicDtoList) {
            System.out.println(mechanic.getId() + " " + mechanic.getName() + " " + mechanic.isMechanicFree());
        }
    }

    @Override
    @Transactional
    public void mechanicsFromCsv() throws ServiceException {

        List<List<String>> lists;

        try (InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(mechanicPath)) {
            try (Reader reader = new InputStreamReader(Objects.requireNonNull(stream))) {
                lists = CsvReader.readRecords(reader);
                createMechanics(lists);
            }
        } catch (CsvException e) {
            logger.warn("Csv Reader exception " + e.getMessage());
        } catch (IOException e) {
            throw new ServiceException("Error read file");
        }
    }

    private void createMechanics(List<List<String>> lists) throws ServiceException {
        List<MechanicDto> loadedMechanics = new ArrayList<>();
        try {
            for (List<String> list : lists) {

                int n = 0;
                int id = Integer.parseInt(list.get(n++));
                String name = list.get(n++);

                boolean exist = false;
                Mechanic mechanicById = mechanicRepository.getById(id);
                MechanicDto newMechanic;
                if (mechanicById != null) {
                    newMechanic = new MechanicDto(mechanicById);
                    exist = true;
                } else {
                    newMechanic = new MechanicDto();
                    newMechanic.setName(name);
                }

                newMechanic.setName(name);

                if (list.size() >= (n + 1)) {
                    List<String> idOrders = Arrays.asList(list.get(n++).split("\\|"));
                    ordersOfMechanic(newMechanic, idOrders);
                }
                if (list.size() >= (n + 1)) {
                    int garageId = Integer.parseInt(list.get(n));
                    GarageDto garage = garageService.getGarageById(garageId);
                    if (garage != null) {
                        newMechanic.setGarageShortDto(garage);
                    }
                }
                if (exist) {
                    updateMechanic(newMechanic);
                } else {
                    loadedMechanics.add(newMechanic);
                }
            }
        } catch (Exception e) {
            throw new ServiceException("Error with create mechanics from csv");
        }

        loadedMechanics.forEach(System.out::println);
        mechanicRepository.addAll(loadedMechanics.stream().map(this::mechanicDtoToEntity).collect(Collectors.toList()));
    }

    private void ordersOfMechanic(MechanicDto newMechanic, List<String> idOrders) throws ServiceException {
        List<OrderShortDto> orders = new ArrayList<>();
        for (String idOrderLine : idOrders) {
            if (!idOrderLine.isBlank()) {
                int idOrder = Integer.parseInt(idOrderLine);
                OrderDto order = orderService.getOrderById(idOrder);
                if (order != null) {
                    orders.add(new OrderShortDto(order));
                    order.setMechanicShortDto(new MechanicShortDto(newMechanic));
                    orderService.updateOrder(order);
                }
            }
        }
        if (orders.size() > 0) {
            newMechanic.setOrderShortDtoList(orders);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public void mechanicsToCsv() {

        List<List<String>> data = new ArrayList<>();

        try {
            File file = CsvWriter.recordFile(mechanicPath);
            List<MechanicDto> mechanicDtoList = mechanicRepository.getAll().stream().map(MechanicDto::new).collect(Collectors.toList());
            for (MechanicDto mechanic: mechanicDtoList) {
                if (mechanic != null) {
                    List<String> dataIn = new ArrayList<>();
                    dataIn.add(String.valueOf(mechanic.getId()));
                    dataIn.add(mechanic.getName());

                    dataIn.add(orderToCsv(mechanic));

                    if (mechanic.getGarageShortDto() != null) {
                        dataIn.add(String.valueOf(mechanic.getGarageShortDto().getId()));
                    }

                    data.add(dataIn);
                }
            }
            CsvWriter.writeRecords(file, headerCsv(), data);
        } catch (CsvException e) {
            logger.warn("Csv write exception " + e.getMessage());
        }
    }

    private String orderToCsv(MechanicDto mechanicDto) {
        StringBuilder ordersString = new StringBuilder();
        for (OrderDto order: orderService.getOrders()) {
            if (order != null && order.getMechanicShortDto().getId() == mechanicDto.getId()) {
                ordersString.append(order.getId());
                ordersString.append("|");
            }
        }
        return ordersString.toString();
    }

    private List<String> headerCsv() {
        List<String> header = new ArrayList<>();
        header.add(CsvMechanicHeader.ID.getName());
        header.add(CsvMechanicHeader.NAME.getName());
        header.add(CsvMechanicHeader.ORDER_IDS.getName());
        header.add(CsvMechanicHeader.GARAGE_ID.getName());
        return header;
    }
}
