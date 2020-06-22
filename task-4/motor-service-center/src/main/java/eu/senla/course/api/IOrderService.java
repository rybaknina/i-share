package eu.senla.course.api;

import eu.senla.course.entity.Mechanic;
import eu.senla.course.entity.Order;
import eu.senla.course.entity.OrderStatus;
import eu.senla.course.entity.Tool;
import eu.senla.course.service.ServiceException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

public interface IOrderService {
    void addOrder(Order order);
    void deleteOrder(Order order);
    Order getOrderById(int id);
    void addToolsToOrder(Order order, List<Tool> tools);
    void changeStatusOrder(Order order, OrderStatus status);
    List<Order> getOrders();
    List<Order> listOrders(Comparator<Order> comparator);
    void changeStartDateOrders(int hours);
    LocalDateTime nextAvailableDate(IGarageService garage, LocalDate endDate);
    Order mechanicOrder(Mechanic mechanic);
    Mechanic orderMechanic(Order order);
    List<Order> ordersForPeriod(Comparator<Order> comparator, OrderStatus status, LocalDateTime startDate, LocalDateTime endDate);
    List<Order> listCurrentOrders(Comparator<Order> comparator);
    void bill(Order order);
    void updateOrder(int id, Order order) throws ServiceException;
    void ordersFromCsv() throws ServiceException;
    void ordersToCsv() throws ServiceException;
}
