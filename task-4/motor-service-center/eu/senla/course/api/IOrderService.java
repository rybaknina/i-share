package eu.senla.course.api;

import eu.senla.course.entity.Mechanic;
import eu.senla.course.entity.Order;
import eu.senla.course.entity.OrderStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

public interface IOrderService {
    void addOrder(Order order);
    List<Order> getOrders();
    List<Order> listOrders(Comparator<Order> comparator);
    void changeStartDateOrders(int hours);
    LocalDateTime nextAvailableDate(IGarageService garage, LocalDate endDate);
    Order mechanicOrder(Mechanic mechanic);
    Mechanic orderMechanic(Order order);
    List<Order> ordersForPeriod(Comparator<Order> comparator, OrderStatus status, LocalDateTime startDate, LocalDateTime endDate);
    List<Order> listCurrentOrders(Comparator<Order> comparator);
    void bill(Order order);
}
