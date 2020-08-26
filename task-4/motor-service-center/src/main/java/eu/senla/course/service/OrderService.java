package eu.senla.course.service;

import eu.senla.course.annotation.di.Injection;
import eu.senla.course.annotation.di.Service;
import eu.senla.course.annotation.property.ConfigProperty;
import eu.senla.course.api.repository.IOrderRepository;
import eu.senla.course.api.service.IOrderService;
import eu.senla.course.controller.*;
import eu.senla.course.entity.Mechanic;
import eu.senla.course.entity.Order;
import eu.senla.course.entity.Spot;
import eu.senla.course.entity.Tool;
import eu.senla.course.enums.ConfigType;
import eu.senla.course.enums.CsvOrderHeader;
import eu.senla.course.enums.OrderStatus;
import eu.senla.course.exception.RepositoryException;
import eu.senla.course.exception.ServiceException;
import eu.senla.course.util.CsvReader;
import eu.senla.course.util.CsvWriter;
import eu.senla.course.util.exception.CsvException;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
public class OrderService implements IOrderService {

    @ConfigProperty(key = "order")
    private String orderPath;
    @ConfigProperty(key = "shift.order.time", type = ConfigType.BOOLEAN)
    private boolean isShiftTime;
    @ConfigProperty(key = "delete.order", type = ConfigType.BOOLEAN)
    private boolean isDeleteOrder;

    @Injection
    private IOrderRepository orderRepository;

    public List<Order> getOrders() {
        return orderRepository.getAll();
    }

    public void setOrders(List<Order> orders) {
        orderRepository.setAll(orders);
    }

    public Order getOrderById(int id) {
        return orderRepository.getById(id);
    }

    public void addOrder(Order order) throws ServiceException {
        try {
            orderRepository.add(order);
        } catch (RepositoryException e) {
            throw new ServiceException("RepositoryException " + e.getMessage());
        }
    }

    public void deleteOrder(Order order) {
        orderRepository.delete(order);
    }

    @Override
    public boolean isDeleteOrder() {
        return isDeleteOrder;
    }

    public void updateOrder(Order order) throws ServiceException {
        try {
            orderRepository.update(order);
        } catch (RepositoryException e) {
            throw new ServiceException("RepositoryException " + e.getMessage());
        }
    }

    public void addToolsToOrder(Order order, List<Tool> tools) throws ServiceException {
        if (order == null) {
            throw new ServiceException("Order is not exist");
        }
        if (tools.size() == 0){
            throw new ServiceException("Tools are not exist");
        } else {
            order.setTools(tools);
        }
    }

    public void changeStatusOrder(Order order, OrderStatus status) throws ServiceException {
        if (order == null) {
            throw new ServiceException("Order is not found");
        }
        order.setStatus(status);
        updateOrder(order);

    }
    public List<Order> ordersForPeriod(Comparator<Order> comparator, OrderStatus status, LocalDateTime startDate, LocalDateTime endDate) throws ServiceException {
        if (orderRepository.getAll().size() == 0){
            throw new ServiceException("Orders are not exist");
        }
        List<Order> ordersForPeriod = new ArrayList<>();
        for (Order order: orderRepository.getAll()){
            if (order!= null && order.getStartDate()!=null && order.getStatus() == status && order.getStartDate().compareTo(startDate) >= 0 && order.getStartDate().compareTo(endDate) <= 0 ){
                ordersForPeriod.add(order);
            }
        }
        ordersForPeriod.sort(comparator);
        return ordersForPeriod;
    }
    public List<Order> listCurrentOrders(Comparator<Order> comparator) throws ServiceException {
        if (orderRepository.getAll().size() == 0){
            throw new ServiceException("Orders are not exist");
        }
        List<Order> currentOrders = new ArrayList<>();
        for (Order order: orderRepository.getAll()){
            if (order != null && order.getStatus() == OrderStatus.IN_PROGRESS){
                currentOrders.add(order);
            }
        }
        currentOrders.sort(comparator);
        return currentOrders;
    }
    public void changeStartDateOrders(int hours) throws ServiceException {
        LocalDateTime date = LocalDateTime.now().plusHours(hours);
        if (orderRepository.getAll().size() == 0){
            throw new ServiceException("Orders are not exist");
        }
        for(Order order: orderRepository.getAll()){
            if (order != null && (order.getStartDate()!=null) && order.getStatus() == OrderStatus.IN_PROGRESS && (order.getCompleteDate()!=null) && date.isAfter(order.getStartDate())){
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

    public List<Order> listOrders(Comparator<Order> comparator) throws ServiceException {
        if (orderRepository.getAll().size() == 0){
            throw new ServiceException("Orders are not exist");
        }
        orderRepository.getAll().sort(comparator);
        return orderRepository.getAll();
    }

    public Order mechanicOrder(Mechanic mechanic) throws ServiceException {
        if (mechanic == null){
            throw new ServiceException("Mechanic does not exist");
        }
        if (orderRepository.getAll().size() == 0){
            throw new ServiceException("Orders are not exist");
        }
        for (Order order: orderRepository.getAll()){
            if (order.getMechanic().getId() == mechanic.getId() && order.getStatus() == OrderStatus.IN_PROGRESS){
                return order;
            }
        }
        return null;
    }

    public Mechanic orderMechanic(Order order) throws ServiceException {
        if (orderRepository.getAll().size() == 0 || order == null){
            throw new ServiceException("Order is not found");
        }
        for (Order orderExist : orderRepository.getAll()) {
            if (orderExist != null && orderExist.getId() == order.getId()) {
                return orderExist.getMechanic();
            }
        }
        return null;
    }
    public LocalDateTime nextAvailableDate(LocalDate endDate) throws ServiceException {
        int days = Period.between(LocalDate.now(), endDate).getDays();
        LocalDateTime nextDate = LocalDateTime.now();
        for (int i=0; i < days; i++) {
            if (GarageController.getInstance().numberAvailableSpots(nextDate, orderRepository.getAll()) > 0) {
                return nextDate;
            } else {
                nextDate = nextDate.plusDays(1);
            }
        }
        System.out.println("Have no free date");
        return null;
    }

    public void bill(Order order) throws ServiceException {
        List<Tool> tools = ToolController.getInstance().getTools();
        if (order == null){
            throw new ServiceException("Order is not exist");
        }
        BigDecimal amount = BigDecimal.ZERO;
        for (Tool tool : tools) {
            if (tool.getOrder().getId() == order.getId()) {
                amount = amount.add(tool.getHourlyPrice().multiply(BigDecimal.valueOf(tool.getHours())));
            }
        }
        order.setPrice(amount);
        updateOrder(order);
        System.out.println("Pay your bill " + order.getPrice() + " for order " + order.getId());

    }


    @Override
    public void ordersFromCsv() throws ServiceException {

        List<List<String>> lists;
        try (InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(orderPath)) {
            try (Reader reader = new InputStreamReader(Objects.requireNonNull(stream))) {
                lists = CsvReader.readRecords(reader);
                createOrders(lists);
            }
        } catch (CsvException e) {
            System.out.println("Csv Reader exception " + e.getMessage());
        } catch (IOException e) {
            throw new ServiceException("Error read file");
        }
    }

    private void createOrders(List<List<String>> lists) throws ServiceException {

        List<Order> loadedOrders = new ArrayList<>();
        try {
            for (List<String> list : lists) {
                int n = 0;

                int id = Integer.parseInt(list.get(n++));
                LocalDateTime requestDate = LocalDateTime.parse(list.get(n++));
                LocalDateTime plannedDate = LocalDateTime.parse(list.get(n++));
                int mechanicId = Integer.parseInt(list.get(n++));
                int spotId = Integer.parseInt(list.get(n++));
                String status = list.get(n);

                Mechanic mechanic = MechanicController.getInstance().getMechanicById(mechanicId);
                Spot spot = SpotController.getInstance().getSpotById(spotId);

                boolean exist = false;

                Order newOrder = orderRepository.getById(id);
                if (newOrder != null) {
                    exist = true;
                } else {
                    newOrder = new Order(requestDate, plannedDate, mechanic, spot);
                }
                if (!exist){
                    newOrder.setRequestDate(LocalDateTime.now());
                } else {
                    setNewOrderStatus(status, newOrder);
                }
                newOrder.setPlannedDate(plannedDate);
                newOrder.setMechanic(mechanic);
                newOrder.setSpot(spot);

                if (exist) {
                    updateOrder(newOrder);
                } else {
                    loadedOrders.add(newOrder);
                }

            }
        } catch (Exception e){
            throw new ServiceException("Error with create orders from csv");
        }

        loadedOrders.forEach(System.out::println);
        orderRepository.addAll(loadedOrders);
    }

    private void setNewOrderStatus(String status, Order newOrder) {
        if (!status.isBlank()){
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
    public void ordersToCsv() {

        List<List<String>> data = new ArrayList<>();

        try {
            File file = CsvWriter.recordFile(orderPath);
            for (Order order: orderRepository.getAll()){
                if (order != null) {
                    List<String> dataIn = new ArrayList<>();
                    dataIn.add(String.valueOf(order.getId()));
                    dataIn.add(String.valueOf(order.getRequestDate()));
                    dataIn.add(String.valueOf(order.getPlannedDate()));
                    if (order.getMechanic() != null) {
                        dataIn.add(String.valueOf(order.getMechanic().getId()));
                    }
                    if (order.getSpot()!= null){
                        dataIn.add(String.valueOf(order.getSpot().getId()));
                    }
                    dataIn.add(String.valueOf(order.getStatus()));

                    data.add(dataIn);
                }
            }
            CsvWriter.writeRecords(file, headerCsv(), data);

        } catch (CsvException e) {
            System.out.println("Csv write exception " + e.getMessage());
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
