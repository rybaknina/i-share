package eu.senla.course.api.service;

import eu.senla.course.entity.Mechanic;
import eu.senla.course.entity.Order;
import eu.senla.course.enums.OrderStatus;
import eu.senla.course.entity.Tool;
import eu.senla.course.exception.ServiceException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

public interface IOrderService {
    void addOrder(Order order) throws ServiceException;
    void deleteOrder(Order order);
    boolean isDeleteOrder();
    Order getOrderById(int id);
    void addToolsToOrder(Order order, List<Tool> tools) throws ServiceException;
    void changeStatusOrder(Order order, OrderStatus status) throws ServiceException;
    List<Order> getOrders();
    void setOrders(List<Order> orders);
    List<Order> listOrders(Comparator<Order> comparator) throws ServiceException;
    void changeStartDateOrders(int hours) throws ServiceException;
    boolean isShiftTime();
    LocalDateTime nextAvailableDate(LocalDate endDate) throws ServiceException;
    Order mechanicOrder(Mechanic mechanic) throws ServiceException;
    Mechanic orderMechanic(Order order) throws ServiceException;
    List<Order> ordersForPeriod(Comparator<Order> comparator, OrderStatus status, LocalDateTime startDate, LocalDateTime endDate) throws ServiceException;
    List<Order> listCurrentOrders(Comparator<Order> comparator) throws ServiceException;
    void bill(Order order) throws ServiceException;
    void updateOrder(Order order) throws ServiceException;
    void ordersFromCsv() throws ServiceException;
    void ordersToCsv();
}
