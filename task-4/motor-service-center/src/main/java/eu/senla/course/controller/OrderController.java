package eu.senla.course.controller;

import eu.senla.course.api.IGarageService;
import eu.senla.course.api.IOrderService;
import eu.senla.course.entity.Mechanic;
import eu.senla.course.entity.Order;
import eu.senla.course.enums.OrderStatus;
import eu.senla.course.entity.Tool;
import eu.senla.course.service.OrderService;
import eu.senla.course.exception.ServiceException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

public class OrderController {
    private final IOrderService service = OrderService.getInstance();
    private final static OrderController instance = new OrderController();

    private OrderController() {
    }

    public static OrderController getInstance(){
        return instance;
    }

    public void addOrder(Order order) throws ServiceException {
        service.addOrder(order);
    }
    public List<Order> getOrders(){
        return service.getOrders();
    }
    public void deleteOrder(Order order) throws ServiceException {
        service.deleteOrder(order);
    }
    public void addToolsToOrder(Order order, List<Tool> tools) throws ServiceException {
        service.addToolsToOrder(order, tools);
    }
    public Order getOrderById(int id) throws ServiceException {
        return service.getOrderById(id);
    }
    public void changeStatusOrder(Order order, OrderStatus status) throws ServiceException {
        service.changeStatusOrder(order, status);
    }
    public void changeStartDateOrders(int hours) throws ServiceException {
        service.changeStartDateOrders(hours);
    }
    public LocalDateTime nextAvailableDate(IGarageService garage, LocalDate endDate) throws ServiceException {
        return service.nextAvailableDate(garage, endDate);
    }
    public Order mechanicOrder(Mechanic mechanic) throws ServiceException {
        return service.mechanicOrder(mechanic);
    }
    public Mechanic orderMechanic(Order order) throws ServiceException {
        return service.orderMechanic(order);
    }
    public List<Order> ordersForPeriod(Comparator<Order> comparator, OrderStatus status, LocalDateTime startDate, LocalDateTime endDate) throws ServiceException {
        return service.ordersForPeriod(comparator, status, startDate, endDate);
    }
    public List<Order> listCurrentOrders(Comparator<Order> comparator) throws ServiceException {
        return service.listCurrentOrders(comparator);
    }
    public void bill(Order order) throws ServiceException {
        service.bill(order);
    }
    public void updateOrder(Order order) throws ServiceException {
        service.updateOrder(order);
    }
    public void ordersFromCsv() throws ServiceException{
        service.ordersFromCsv();
    }
    public void ordersToCsv() throws ServiceException{
        service.ordersToCsv();
    }
}