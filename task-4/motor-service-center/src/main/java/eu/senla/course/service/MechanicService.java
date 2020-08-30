package eu.senla.course.service;

import eu.senla.course.annotation.di.Injection;
import eu.senla.course.annotation.di.Service;
import eu.senla.course.annotation.property.ConfigProperty;
import eu.senla.course.api.repository.IMechanicRepository;
import eu.senla.course.api.service.IMechanicService;
import eu.senla.course.controller.GarageController;
import eu.senla.course.controller.OrderController;
import eu.senla.course.entity.Garage;
import eu.senla.course.entity.Mechanic;
import eu.senla.course.entity.Order;
import eu.senla.course.enums.CsvMechanicHeader;
import eu.senla.course.exception.RepositoryException;
import eu.senla.course.exception.ServiceException;
import eu.senla.course.util.CsvReader;
import eu.senla.course.util.CsvWriter;
import eu.senla.course.util.exception.CsvException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.*;



@Service
public class MechanicService implements IMechanicService {
    private final static Logger logger = LogManager.getLogger(MechanicService.class);
    @ConfigProperty(key = "mechanic")
    private String mechanicPath;
    @Injection
    private IMechanicRepository mechanicRepository;

    public void addMechanic(Mechanic mechanic) throws ServiceException {
        try {
            mechanicRepository.add(mechanic);
        } catch (RepositoryException e) {
            throw new ServiceException("RepositoryException " + e.getMessage());
        }
    }

    public List<Mechanic> getMechanics() {
        return mechanicRepository.getAll();
    }

    public void setMechanics(List<Mechanic> mechanics) {
        mechanicRepository.setAll(mechanics);
    }

    public void deleteMechanic(Mechanic mechanic) {
        mechanicRepository.delete(mechanic);
    }

    public Mechanic getMechanicById(int id) {
        return mechanicRepository.getById(id);
    }

    public void updateMechanic(Mechanic mechanic) throws ServiceException {
        try {
            mechanicRepository.update(mechanic);
        } catch (RepositoryException e) {
            throw new ServiceException("RepositoryException " + e.getMessage());
        }
    }

    public Mechanic firstFreeMechanic() throws ServiceException {
        if (mechanicRepository.getAll().size() == 0) {
            throw new ServiceException("Auto mechanics are not exist");
        }
        for (Mechanic mechanic: mechanicRepository.getAll()) {
            if (mechanic.isMechanicFree()) {
                return mechanic;
            }
        }
        System.out.println("Free mechanic is not found");
        return null;
    }

    public void sortMechanicsBy(Comparator<Mechanic> comparator) throws ServiceException {
        if (mechanicRepository.getAll().size() == 0) {
            throw new ServiceException("Auto mechanics are not exist");
        }
        mechanicRepository.getAll().sort(comparator);
        for (Mechanic mechanic: mechanicRepository.getAll()) {
            System.out.println(mechanic.getId() + " " + mechanic.getName() + " " + mechanic.isMechanicFree());
        }
    }

    @Override
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
        List<Mechanic> loadedMechanics = new ArrayList<>();
        try {
            for (List<String> list : lists) {

                int n = 0;
                int id = Integer.parseInt(list.get(n++));
                String name = list.get(n++);

                boolean exist = false;
                Mechanic newMechanic = mechanicRepository.getById(id);
                if (newMechanic != null) {
                    exist = true;
                } else {
                    newMechanic = new Mechanic(name);
                }

                newMechanic.setName(name);

                if (list.size() >= (n + 1)) {
                    List<String> idOrders = Arrays.asList(list.get(n++).split("\\|"));
                    ordersOfMechanic(newMechanic, idOrders);
                }
                if (list.size() >= (n + 1)) {
                    int garageId = Integer.parseInt(list.get(n));
                    Garage garage = GarageController.getInstance().getGarageById(garageId);
                    if (garage != null) {
                        newMechanic.setGarage(garage);
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
        mechanicRepository.addAll(loadedMechanics);
    }

    private void ordersOfMechanic(Mechanic newMechanic, List<String> idOrders) throws ServiceException {
        List<Order> orders = new ArrayList<>();
        for (String idOrderLine : idOrders) {
            if (!idOrderLine.isBlank()) {
                int idOrder = Integer.parseInt(idOrderLine);
                Order order = OrderController.getInstance().getOrderById(idOrder);
                if (order != null) {
                    orders.add(order);
                    order.setMechanic(newMechanic);
                    OrderController.getInstance().updateOrder(order);
                }
            }
        }
        if (orders.size() > 0) {
            newMechanic.setOrders(orders);
        }
    }

    @Override
    public void mechanicsToCsv() {

        List<List<String>> data = new ArrayList<>();

        try {
            File file = CsvWriter.recordFile(mechanicPath);
            for (Mechanic mechanic: mechanicRepository.getAll()) {
                if (mechanic != null) {
                    List<String> dataIn = new ArrayList<>();
                    dataIn.add(String.valueOf(mechanic.getId()));
                    dataIn.add(mechanic.getName());

                    dataIn.add(orderToCsv(mechanic));

                    if (mechanic.getGarage() != null) {
                        dataIn.add(String.valueOf(mechanic.getGarage().getId()));
                    }

                    data.add(dataIn);
                }
            }
            CsvWriter.writeRecords(file, headerCsv(), data);
        } catch (CsvException e) {
            logger.warn("Csv write exception " + e.getMessage());
        }
    }

    private String orderToCsv(Mechanic mechanic) {
        StringBuilder ordersString = new StringBuilder();
        for (Order order: OrderController.getInstance().getOrders()) {
            if (order != null && order.getMechanic().equals(mechanic)) {
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
