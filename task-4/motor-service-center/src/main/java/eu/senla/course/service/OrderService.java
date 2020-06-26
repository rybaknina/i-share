package eu.senla.course.service;

import eu.senla.course.api.IGarageService;
import eu.senla.course.api.IOrderService;
import eu.senla.course.entity.*;
import eu.senla.course.enums.CsvOrderHeader;
import eu.senla.course.enums.OrderStatus;
import eu.senla.course.exception.ServiceException;
import eu.senla.course.util.exception.CsvException;
import eu.senla.course.util.CsvReader;
import eu.senla.course.util.CsvWriter;
import eu.senla.course.util.PathToFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class OrderService implements IOrderService {

    private final static IOrderService instance = new OrderService();
    private final static String ORDER_PATH = "order";

    private List<Order> orders;

    private OrderService() {
        this.orders = new ArrayList<>();
    }

    public static IOrderService getInstance(){
        return instance;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Order getOrderById(int id) throws ServiceException {
        if (orders.size() == 0 || orders.get(id) == null){
            throw new ServiceException("Order is not found");
        }
        return orders.get(id);

    }

    public void addOrder(Order order) throws ServiceException {
        if (order == null){
            throw new ServiceException("Order is not exist");
        }
        orders.add(order);
    }

    public void deleteOrder(Order order) throws ServiceException {
        if (order == null || orders.size() == 0) {
            throw new ServiceException("Order is not found");
        }
        orders.removeIf(e -> e.equals(order));

    }

    public void updateOrder(Order order) throws ServiceException {
        Optional.ofNullable(orders.get(order.getId()-1)).orElseThrow(() -> new ServiceException("Order is not found"));
        orders.set(order.getId()-1, order);
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

    }
    public List<Order> ordersForPeriod(Comparator<Order> comparator, OrderStatus status, LocalDateTime startDate, LocalDateTime endDate) throws ServiceException {
        if (orders.size() == 0){
            throw new ServiceException("Orders are not exist");
        }
        List<Order> ordersForPeriod = new ArrayList<>();
        for (Order order: orders){
            if (order!= null && order.getStartDate()!=null && order.getStatus() == status && order.getStartDate().compareTo(startDate) >= 0 && order.getStartDate().compareTo(endDate) <= 0 ){
                ordersForPeriod.add(order);
            }
        }
        ordersForPeriod.sort(comparator);
        return ordersForPeriod;
    }
    public List<Order> listCurrentOrders(Comparator<Order> comparator) throws ServiceException {
        if (orders.size() == 0){
            throw new ServiceException("Orders are not exist");
        }
        List<Order> currentOrders = new ArrayList<>();
        for (Order order: orders){
            if (order != null && order.getStatus() == OrderStatus.IN_PROGRESS){
                currentOrders.add(order);
            }
        }
        currentOrders.sort(comparator);
        return currentOrders;
    }
    public void changeStartDateOrders(int hours) throws ServiceException {
        LocalDateTime date = LocalDateTime.now().plusHours(hours);
        if (orders.size() == 0){
            throw new ServiceException("Orders are not exist");
        }
        for(Order order: orders){
            if (order != null && (order.getStartDate()!=null) && order.getStatus() == OrderStatus.IN_PROGRESS && (order.getCompleteDate()!=null) && date.isAfter(order.getStartDate())){
                order.setStartDate(order.getStartDate().plusHours(hours));
                if (order.getCompleteDate() != null) {
                    order.setCompleteDate(order.getCompleteDate().plusHours(hours));
                }

            }
        }
    }

    public List<Order> listOrders(Comparator<Order> comparator) throws ServiceException {
        if (orders.size() == 0){
            throw new ServiceException("Orders are not exist");
        }
        orders.sort(comparator);
        return orders;
    }

    public Order mechanicOrder(Mechanic mechanic) throws ServiceException {
        if (mechanic == null){
            throw new ServiceException("Mechanic does not exist");
        }
        if (orders.size() == 0){
            throw new ServiceException("Orders are not exist");
        }
        for (Order order: orders){
            if (order.getStatus() == OrderStatus.IN_PROGRESS){
                return order;
            }
        }
        return null;
    }

    public Mechanic orderMechanic(Order order) throws ServiceException {
        if (orders.size() == 0){
            throw new ServiceException("Orders are not exist");

        }
        for (Order orderExist : orders) {
            if (orderExist != null && orderExist.equals(order)) {
                return orderExist.getMechanic();
            }
        }
        return null;
    }
    public LocalDateTime nextAvailableDate(IGarageService garage, LocalDate endDate) throws ServiceException {
        int days = Period.between(LocalDate.now(), endDate).getDays();
        LocalDateTime nextDate = LocalDateTime.now();
        for (int i=0; i < days; i++) {
            if (garage.numberAvailableSpots(nextDate, orders) > 0) {
                return nextDate;
            } else {
                nextDate = nextDate.plusDays(1);
            }
        }
        System.out.println("Have no free date");
        return null;
    }

    public void bill(Order order) throws ServiceException {
        if (order == null){
            throw new ServiceException("Order is not exist");
        }
        if (order.getTools() == null || order.getTools().size() == 0){
            System.out.println("Order has no services...");
        } else {
            BigDecimal amount = BigDecimal.ZERO;
            for (Tool service : order.getTools()) {
                amount = amount.add(service.getHourlyPrice().multiply(BigDecimal.valueOf(service.getHours())));
            }
            order.setPrice(amount);
            System.out.println("Pay your bill " + order.getPrice() + " for order " + order.getId());
        }
    }

    private Path getPath() throws ServiceException {
        Path path = Optional.of(Paths.get(new PathToFile().getPath(ORDER_PATH))).orElseThrow(() -> new ServiceException("Something wrong with path"));
        return path;
    }

    @Override
    public void ordersFromCsv() throws ServiceException {

        List<List<String>> lists;
        Path path = this.getPath();

        try {
            lists = CsvReader.readRecords(Files.newBufferedReader(path));
            createOrders(lists);

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

                int id = Integer.parseInt(list.get(n++)) - 1;
                LocalDateTime requestDate = LocalDateTime.parse(list.get(n++));
                LocalDateTime plannedDate = LocalDateTime.parse(list.get(n++));
                int mechanicId = Integer.parseInt(list.get(n++)) - 1;
                int spotId = Integer.parseInt(list.get(n++)) - 1;
                String status = list.get(n);

                Mechanic mechanic = null;
                if (MechanicService.getInstance().getMechanics().size() >= (mechanicId + 1)) {
                    mechanic = MechanicService.getInstance().getMechanicById(mechanicId);
                }
                Spot spot = null;
                if (SpotService.getInstance().getSpots().size() >= (spotId + 1)) {
                    spot = SpotService.getInstance().getSpotById(spotId);
                }

                boolean exist = false;


                Order newOrder;
                if (orders.size() >= (id + 1) && orders.get(id) != null) {
                    newOrder = orders.get(id);
                    exist = true;
                } else {
                    newOrder = new Order(requestDate, plannedDate, mechanic, spot);
                }
                if (!exist){
                    newOrder.setRequestDate(LocalDateTime.now());
                } else {
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
                newOrder.setPlannedDate(plannedDate);

                if (mechanic != null) {
                    newOrder.setMechanic(mechanic);
                }
                if (spot != null){
                    newOrder.setSpot(spot);
                }

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
        orders.addAll(loadedOrders);
    }

    @Override
    public void ordersToCsv() throws ServiceException {
        // TODO: need to test
        List<List<String>> data = new ArrayList<>();

        List<String> headers = new ArrayList<>();
        headers.add(CsvOrderHeader.ID.getName());
        headers.add(CsvOrderHeader.REQUEST_DATE.getName());
        headers.add(CsvOrderHeader.PLANNED_DATE.getName());
        headers.add(CsvOrderHeader.MECHANIC_ID.getName());
        headers.add(CsvOrderHeader.SPOT_ID.getName());
        headers.add(CsvOrderHeader.STATUS.getName());
        try {
            for (Order order: orders){
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
            CsvWriter.writeRecords(new File(String.valueOf(getPath())), headers, data);

        } catch (CsvException e) {
            System.out.println("Csv write exception" + e.getMessage());
        }
    }
}