package eu.senla.course.service;

import eu.senla.course.api.repository.IOrderRepository;
import eu.senla.course.api.service.*;
import eu.senla.course.dto.mechanic.MechanicDto;
import eu.senla.course.dto.mechanic.MechanicShortDto;
import eu.senla.course.dto.order.OrderDto;
import eu.senla.course.dto.spot.SpotDto;
import eu.senla.course.dto.spot.SpotShortDto;
import eu.senla.course.dto.tool.ToolDto;
import eu.senla.course.dto.tool.ToolShortDto;
import eu.senla.course.entity.Order;
import eu.senla.course.enums.CsvOrderHeader;
import eu.senla.course.enums.OrderStatus;
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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component("orderService")
public class OrderService implements IOrderService {
    private final static Logger logger = LogManager.getLogger(OrderService.class);
    @Value("${order}")
    private String orderPath;
    @Value("${shift.order.time}")
    private boolean isShiftTime;
    @Value("${delete.order}")
    private boolean isDeleteOrder;


    private IOrderRepository orderRepository;
    private IGarageService garageService;
    private IToolService toolService;
    private IMechanicService mechanicService;
    private ISpotService spotService;

    public Order orderDtoToEntity(OrderDto orderDto) {
        Order order = new Order();
        order.setId(orderDto.getId());
        order.setRequestDate(orderDto.getRequestDate());
        order.setPlannedDate(orderDto.getPlannedDate());
        order.setStartDate(orderDto.getStartDate());
        order.setCompleteDate(orderDto.getCompleteDate());
        if (orderDto.getMechanicShortDto() != null) {
            order.setMechanic(mechanicService.mechanicShortDtoToEntity(orderDto.getMechanicShortDto()));
        }
        if (orderDto.getSpotShortDto() != null) {
            order.setSpot(spotService.spotShortDtoToEntity(orderDto.getSpotShortDto()));
        }
        order.setPrice(orderDto.getPrice());
        order.setStatus(orderDto.getStatus());

        return order;
    }

    @Autowired
    @Qualifier("orderHibernateRepository")
    public void setOrderRepository(IOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Autowired
    @Qualifier("garageService")
    public void setGarageService(IGarageService garageService) {
        this.garageService = garageService;
    }

    @Autowired
    @Qualifier("toolService")
    public void setToolService(IToolService toolService) {
        this.toolService = toolService;
    }

    @Autowired
    @Qualifier("mechanicService")
    public void setMechanicService(IMechanicService mechanicService) {
        this.mechanicService = mechanicService;
    }

    @Autowired
    @Qualifier("spotService")
    public void setSpotService(ISpotService spotService) {
        this.spotService = spotService;
    }

    @Transactional(readOnly = true)
    public List<OrderDto> getOrders() {
        return orderRepository
               .getAll()
               .stream()
               .map(OrderDto::new)
               .collect(Collectors.toList());
    }

    @Transactional
    public void setOrders(List<OrderDto> orderDtoList) {
        orderRepository.setAll(orderDtoList.stream().map(this::orderDtoToEntity).collect(Collectors.toList()));
    }

    @Transactional(readOnly = true)
    public OrderDto getOrderById(int id) {
        return new OrderDto(orderRepository.getById(id));
    }

    @Transactional
    public void addOrder(OrderDto orderDto) throws ServiceException {
        try {
            orderRepository.add(orderDtoToEntity(orderDto));
        } catch (RepositoryException e) {
            throw new ServiceException("RepositoryException " + e.getMessage());
        }
    }

    @Transactional
    public void deleteOrder(int id) {
        orderRepository.delete(id);
    }

    @Override
    public boolean isDeleteOrder() {
        return isDeleteOrder;
    }

    @Transactional
    public void updateOrder(OrderDto orderDto) throws ServiceException {
        try {
            orderRepository.update(orderDtoToEntity(orderDto));
        } catch (RepositoryException e) {
            throw new ServiceException("RepositoryException " + e.getMessage());
        }
    }

    @Transactional
    public void addToolsToOrder(OrderDto orderDto, List<ToolDto> toolDtoList) throws ServiceException {
        if (orderDto == null) {
            throw new ServiceException("Order is not exist");
        }
        if (toolDtoList.size() == 0) {
            throw new ServiceException("Tools are not exist");
        } else {
            orderDto.setToolShortDtoList(toolDtoList.stream().map(ToolShortDto::new).collect(Collectors.toList()));
        }
    }

    @Transactional
    public void changeStatusOrder(OrderDto orderDto, OrderStatus status) throws ServiceException {
        if (orderDto == null) {
            throw new ServiceException("Order is not found");
        }
        orderDto.setStatus(status);
        updateOrder(orderDto);
    }

    @Transactional(readOnly = true)
    public List<OrderDto> ordersForPeriod(Comparator<OrderDto> comparator, OrderStatus status, LocalDateTime startDate, LocalDateTime endDate) throws ServiceException {
        if (orderRepository.getAll().size() == 0) {
            throw new ServiceException("Orders are not exist");
        }
        List<OrderDto> ordersForPeriod = new ArrayList<>();
        List<OrderDto> orderDtoList = orderRepository.getAll().stream().map(OrderDto::new).collect(Collectors.toList());
        for (OrderDto order: orderDtoList) {
            if (order !=  null && order.getStartDate() != null && order.getStatus() == status && order.getStartDate().compareTo(startDate) >= 0 && order.getStartDate().compareTo(endDate) <= 0) {
                ordersForPeriod.add(order);
            }
        }
        ordersForPeriod.sort(comparator);
        return ordersForPeriod;
    }

    @Transactional(readOnly = true)
    public List<OrderDto> listCurrentOrders(Comparator<OrderDto> comparator) throws ServiceException {
        if (orderRepository.getAll().size() == 0) {
            throw new ServiceException("Orders are not exist");
        }
        List<OrderDto> currentOrders = new ArrayList<>();
        List<OrderDto> orderDtoList = orderRepository.getAll().stream().map(OrderDto::new).collect(Collectors.toList());
        for (OrderDto order: orderDtoList) {
            if (order != null && order.getStatus() == OrderStatus.IN_PROGRESS) {
                currentOrders.add(order);
            }
        }
        currentOrders.sort(comparator);
        return currentOrders;
    }

    @Transactional
    public void changeStartDateOrders(int hours) throws ServiceException {
        LocalDateTime date = LocalDateTime.now().plusHours(hours);
        if (orderRepository.getAll().size() == 0) {
            throw new ServiceException("Orders are not exist");
        }
        List<OrderDto> orderDtoList = orderRepository.getAll().stream().map(OrderDto::new).collect(Collectors.toList());
        for (OrderDto order: orderDtoList) {
            if (order != null && (order.getStartDate() != null) && order.getStatus() == OrderStatus.IN_PROGRESS && (order.getCompleteDate() != null) && date.isAfter(order.getStartDate())) {
                order.setStartDate(order.getStartDate().plusHours(hours));
                if (order.getCompleteDate() != null) {
                    order.setCompleteDate(order.getCompleteDate().plusHours(hours));
                    updateOrder(order);
                }
            }
        }
    }

    @Override
    public boolean isShiftTime() {
        return isShiftTime;
    }

    @Transactional(readOnly = true)
    public List<OrderDto> listOrders(Comparator<OrderDto> comparator) throws ServiceException {
        if (orderRepository.getAll().size() == 0) {
            throw new ServiceException("Orders are not exist");
        }
        List<OrderDto> orderDtoList = orderRepository.getAll().stream().map(OrderDto::new).sorted(comparator).collect(Collectors.toList());
        return orderDtoList;
    }

    @Transactional(readOnly = true)
    public OrderDto mechanicOrder(MechanicDto mechanicDto) throws ServiceException {
        if (mechanicDto == null) {
            throw new ServiceException("Mechanic does not exist");
        }
        List<OrderDto> orderDtoList = orderRepository.getAll().stream().map(OrderDto::new).collect(Collectors.toList());
        if (orderDtoList.size() == 0) {
            throw new ServiceException("Orders are not exist");
        }
        for (OrderDto order: orderDtoList) {
            if (order.getMechanicShortDto().getId() == mechanicDto.getId() && order.getStatus() == OrderStatus.IN_PROGRESS) {
                return order;
            }
        }
        return null;
    }

    @Transactional(readOnly = true)
    public MechanicDto orderMechanic(OrderDto order) throws ServiceException {
        List<OrderDto> orderDtoList = orderRepository.getAll().stream().map(OrderDto::new).collect(Collectors.toList());
        if (orderDtoList.size() == 0 || order == null) {
            throw new ServiceException("Order is not found");
        }
        for (OrderDto orderExist : orderDtoList) {
            if (orderExist != null && orderExist.getId() == order.getId()) {
                MechanicDto mechanicDto = new MechanicDto();
                mechanicDto.setId(orderExist.getMechanicShortDto().getId());
                mechanicDto.setName(orderExist.getMechanicShortDto().getName());
                return mechanicDto;
            }
        }
        return null;
    }

    @Transactional(readOnly = true)
    public LocalDateTime nextAvailableDate(LocalDate endDate) throws ServiceException {
        int days = Period.between(LocalDate.now(), endDate).getDays();
        LocalDateTime nextDate = LocalDateTime.now();
        List<OrderDto> orderDtoList = orderRepository.getAll().stream().map(OrderDto::new).collect(Collectors.toList());
        for (int i = 0; i < days; i++) {
            if (garageService.numberAvailableSpots(nextDate, orderDtoList) > 0) {
                return nextDate;
            } else {
                nextDate = nextDate.plusDays(1);
            }
        }
        System.out.println("Have no free date");
        return null;
    }

    @Transactional
    public BigDecimal bill(OrderDto orderDto) throws ServiceException {
        List<ToolDto> tools = toolService.getTools();
        if (orderDto == null) {
            throw new ServiceException("Order is not exist");
        }
        BigDecimal amount = BigDecimal.ZERO;
        for (ToolDto tool : tools) {
            if (tool.getOrderDto().getId() == orderDto.getId()) {
                amount = amount.add(tool.getHourlyPrice().multiply(BigDecimal.valueOf(tool.getHours())));
            }
        }
        orderDto.setPrice(amount);
        updateOrder(orderDto);
        System.out.println("Pay your bill " + orderDto.getPrice() + " for order " + orderDto.getId());
        return orderDto.getPrice();
    }


    @Override
    @Transactional
    public void ordersFromCsv() throws ServiceException {

        List<List<String>> lists;
        try (InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(orderPath)) {
            try (Reader reader = new InputStreamReader(Objects.requireNonNull(stream))) {
                lists = CsvReader.readRecords(reader);
                createOrders(lists);
            }
        } catch (CsvException e) {
            logger.warn("Csv Reader exception " + e.getMessage());
        } catch (IOException e) {
            throw new ServiceException("Error read file");
        }
    }

    private void createOrders(List<List<String>> lists) throws ServiceException {

        List<OrderDto> loadedOrders = new ArrayList<>();
        try {
            for (List<String> list : lists) {
                int n = 0;

                int id = Integer.parseInt(list.get(n++));
                LocalDateTime requestDate = LocalDateTime.parse(list.get(n++));
                LocalDateTime plannedDate = LocalDateTime.parse(list.get(n++));
                int mechanicId = Integer.parseInt(list.get(n++));
                int spotId = Integer.parseInt(list.get(n++));
                String status = list.get(n);

                MechanicDto mechanic = mechanicService.getMechanicById(mechanicId);
                SpotDto spot = spotService.getSpotById(spotId);

                boolean exist = false;

                Order order = orderRepository.getById(id);
                OrderDto newOrder;
                if (order != null) {
                    newOrder = new OrderDto(order);
                    exist = true;
                } else {
                    newOrder = new OrderDto();
                    newOrder.setRequestDate(requestDate);
                }
                if (!exist) {
                    newOrder.setRequestDate(LocalDateTime.now());
                } else {
                    setNewOrderStatus(status, newOrder);
                }
                newOrder.setPlannedDate(plannedDate);
                newOrder.setMechanicShortDto(new MechanicShortDto(mechanic));
                newOrder.setSpotShortDto(new SpotShortDto(spot));

                if (exist) {
                    updateOrder(newOrder);
                } else {
                    loadedOrders.add(newOrder);
                }
            }
        } catch (Exception e) {
            throw new ServiceException("Error with create orders from csv");
        }

        loadedOrders.forEach(System.out::println);
        orderRepository.addAll(loadedOrders.stream().map(this::orderDtoToEntity).collect(Collectors.toList()));
    }

    private void setNewOrderStatus(String status, OrderDto newOrder) {
        if (!status.isEmpty()) {
            switch (status) {
                case "CLOSE":
                    newOrder.setStatus(OrderStatus.CLOSE);
                    break;
                case "DELETE":
                    newOrder.setStatus(OrderStatus.DELETE);
                    break;
                case "CANCEL":
                    newOrder.setStatus(OrderStatus.CANCEL);
                    break;
                default:
                    newOrder.setStatus(OrderStatus.IN_PROGRESS);
                    break;
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public void ordersToCsv() {

        List<List<String>> data = new ArrayList<>();

        try {
            File file = CsvWriter.recordFile(orderPath);
            for (Order order: orderRepository.getAll()) {
                if (order != null) {
                    List<String> dataIn = new ArrayList<>();
                    dataIn.add(String.valueOf(order.getId()));
                    dataIn.add(String.valueOf(order.getRequestDate()));
                    dataIn.add(String.valueOf(order.getPlannedDate()));
                    if (order.getMechanic() != null) {
                        dataIn.add(String.valueOf(order.getMechanic().getId()));
                    }
                    if (order.getSpot() != null) {
                        dataIn.add(String.valueOf(order.getSpot().getId()));
                    }
                    dataIn.add(String.valueOf(order.getStatus()));

                    data.add(dataIn);
                }
            }
            CsvWriter.writeRecords(file, headerCsv(), data);
        } catch (CsvException e) {
            logger.warn("Csv write exception " + e.getMessage());
        }
    }

    private List<String> headerCsv() {
        List<String> header = new ArrayList<>();
        header.add(CsvOrderHeader.ID.getName());
        header.add(CsvOrderHeader.REQUEST_DATE.getName());
        header.add(CsvOrderHeader.PLANNED_DATE.getName());
        header.add(CsvOrderHeader.MECHANIC_ID.getName());
        header.add(CsvOrderHeader.SPOT_ID.getName());
        header.add(CsvOrderHeader.STATUS.getName());
        return header;
    }
}
