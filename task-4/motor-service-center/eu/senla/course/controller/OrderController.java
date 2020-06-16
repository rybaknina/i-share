package eu.senla.course.controller;

import eu.senla.course.api.IGarageService;
import eu.senla.course.api.IOrderService;
import eu.senla.course.entity.Mechanic;
import eu.senla.course.entity.Order;
import eu.senla.course.entity.OrderStatus;
import eu.senla.course.entity.Tool;
import eu.senla.course.service.OrderService;

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

    public void addOrder(Order order){
        service.addOrder(order);
    }
    public List<Order> getOrders(){
        return service.getOrders();
    }
    public void deleteOrder(Order order){
        service.deleteOrder(order);
    }
    public void addToolsToOrder(Order order, List<Tool> tools){
        service.addToolsToOrder(order, tools);
    }
    public Order getOrderById(int id){
        return service.getOrderById(id);
    }
    public void changeStatusOrder(Order order, OrderStatus status){
        service.changeStatusOrder(order, status);
    }
    public void changeStartDateOrders(int hours){
        service.changeStartDateOrders(hours);
    }
    public LocalDateTime nextAvailableDate(IGarageService garage, LocalDate endDate){
        return service.nextAvailableDate(garage, endDate);
    }
    public Order mechanicOrder(Mechanic mechanic){
        return service.mechanicOrder(mechanic);
    }
    public Mechanic orderMechanic(Order order){
        return service.orderMechanic(order);
    }
    public List<Order> ordersForPeriod(Comparator<Order> comparator, OrderStatus status, LocalDateTime startDate, LocalDateTime endDate){
        return service.ordersForPeriod(comparator, status, startDate, endDate);
    }
    public List<Order> listCurrentOrders(Comparator<Order> comparator){
        return service.listCurrentOrders(comparator);
    }
    public void bill(Order order){
        service.bill(order);
    }
}
