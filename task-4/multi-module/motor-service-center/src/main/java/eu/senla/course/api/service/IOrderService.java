package eu.senla.course.api.service;

import eu.senla.course.dto.mechanic.MechanicDto;
import eu.senla.course.dto.order.OrderDto;
import eu.senla.course.dto.tool.ToolDto;
import eu.senla.course.entity.Order;
import eu.senla.course.enums.OrderStatus;
import eu.senla.course.exception.ServiceException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

public interface IOrderService {
    Order orderDtoToEntity(OrderDto orderDto);
    void addOrder(OrderDto orderDto) throws ServiceException;
    void deleteOrder(int id);
    boolean isDeleteOrder();
    OrderDto getOrderById(int id);
    void addToolsToOrder(OrderDto orderDto, List<ToolDto> toolDtoList) throws ServiceException;
    void changeStatusOrder(OrderDto orderDto, OrderStatus status) throws ServiceException;
    List<OrderDto> getOrders();
    void setOrders(List<OrderDto> orderDtoList);
    List<OrderDto> listOrders(Comparator<OrderDto> comparator) throws ServiceException;
    void changeStartDateOrders(int hours) throws ServiceException;
    boolean isShiftTime();
    LocalDateTime nextAvailableDate(LocalDate endDate) throws ServiceException;
    OrderDto mechanicOrder(MechanicDto mechanicDto) throws ServiceException;
    MechanicDto orderMechanic(OrderDto orderDto) throws ServiceException;
    List<OrderDto> ordersForPeriod(Comparator<OrderDto> comparator, OrderStatus status, LocalDateTime startDate, LocalDateTime endDate) throws ServiceException;
    List<OrderDto> listCurrentOrders(Comparator<OrderDto> comparator) throws ServiceException;
    BigDecimal bill(OrderDto orderDto) throws ServiceException;
    void updateOrder(OrderDto orderDto) throws ServiceException;
    void ordersFromCsv() throws ServiceException;
    void ordersToCsv();
}
