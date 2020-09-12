package eu.senla.course.controller;

import eu.senla.course.api.service.IOrderService;
import eu.senla.course.entity.Mechanic;
import eu.senla.course.entity.Order;
import eu.senla.course.entity.Tool;
import eu.senla.course.enums.OrderStatus;
import eu.senla.course.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Component
final public class OrderController {

    private IOrderService service;
    private static OrderController instance;

    private OrderController() {
    }

    public static OrderController getInstance() {
        if (instance == null) {
            instance = new OrderController();
        }
        return instance;
    }

    @Autowired
    public void setController(OrderController controller) {
        this.instance = controller;
    }
    @Autowired
    public void setService(IOrderService service) {
        this.service = service;
    }

    public void addOrder(Order order) throws ServiceException {
        service.addOrder(order);
    }
    public List<Order> getOrders() {
        return service.getOrders();
    }
    public void deleteOrder(int id) {
        service.deleteOrder(id);
    }
    public boolean isDeleteOrder() {
        return service.isDeleteOrder();
    }
    public void addToolsToOrder(Order order, List<Tool> tools) throws ServiceException {
        service.addToolsToOrder(order, tools);
    }
    public Order getOrderById(int id) {
        return service.getOrderById(id);
    }
    public void changeStatusOrder(Order order, OrderStatus status) throws ServiceException {
        service.changeStatusOrder(order, status);
    }
    public boolean isShiftTime() {
        return service.isShiftTime();
    }
    public List<Order> listOrders(Comparator<Order> comparator) throws ServiceException {
        return service.listOrders(comparator);
    }
    public void changeStartDateOrders(int hours) throws ServiceException {
        service.changeStartDateOrders(hours);
    }
    public LocalDateTime nextAvailableDate(LocalDate endDate) throws ServiceException {
        return service.nextAvailableDate(endDate);
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
    public void ordersFromCsv() throws ServiceException {
        service.ordersFromCsv();
    }
    public void ordersToCsv() {
        service.ordersToCsv();
    }
}
