package eu.senla.course.controller;

import eu.senla.course.api.service.IOrderService;
import eu.senla.course.dto.mechanic.MechanicDto;
import eu.senla.course.dto.order.OrderDto;
import eu.senla.course.dto.tool.ToolDto;
import eu.senla.course.enums.OrderStatus;
import eu.senla.course.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@RestController
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
    @Qualifier("orderService")
    public void setService(IOrderService service) {
        this.service = service;
    }

    @PostMapping("/order")
    public void addOrder(@RequestBody OrderDto orderDto) throws ServiceException {
        service.addOrder(orderDto);
    }

    @GetMapping("/orders")
    public List<OrderDto> getOrders() {
        return service.getOrders();
    }

    @DeleteMapping("/order/{id}")
    public void deleteOrder(@PathVariable int id) {
        service.deleteOrder(id);
    }
    public boolean isDeleteOrder() {
        return service.isDeleteOrder();
    }

    public void addToolsToOrder(OrderDto orderDto, List<ToolDto> toolDtoList) throws ServiceException {
        service.addToolsToOrder(orderDto, toolDtoList);
    }

    @GetMapping("/order/{id}")
    public OrderDto getOrderById(@PathVariable int id) {
        return service.getOrderById(id);
    }
    public void changeStatusOrder(OrderDto orderDto, OrderStatus status) throws ServiceException {
        service.changeStatusOrder(orderDto, status);
    }
    public boolean isShiftTime() {
        return service.isShiftTime();
    }
    public List<OrderDto> listOrders(Comparator<OrderDto> comparator) throws ServiceException {
        return service.listOrders(comparator);
    }

    public void changeStartDateOrders(int hours) throws ServiceException {
        service.changeStartDateOrders(hours);
    }
    public LocalDateTime nextAvailableDate(LocalDate endDate) throws ServiceException {
        return service.nextAvailableDate(endDate);
    }
    public OrderDto mechanicOrder(MechanicDto mechanicDto) throws ServiceException {
        return service.mechanicOrder(mechanicDto);
    }
    public MechanicDto orderMechanic(OrderDto orderDto) throws ServiceException {
        return service.orderMechanic(orderDto);
    }
    public List<OrderDto> ordersForPeriod(Comparator<OrderDto> comparator, OrderStatus status, LocalDateTime startDate, LocalDateTime endDate) throws ServiceException {
        return service.ordersForPeriod(comparator, status, startDate, endDate);
    }
    public List<OrderDto> listCurrentOrders(Comparator<OrderDto> comparator) throws ServiceException {
        return service.listCurrentOrders(comparator);
    }
    public BigDecimal bill(OrderDto orderDto) throws ServiceException {
        return service.bill(orderDto);
    }

    @PutMapping("/order")
    public void updateOrder(@RequestBody OrderDto orderDto) throws ServiceException {
        service.updateOrder(orderDto);
    }
    public void ordersFromCsv() throws ServiceException {
        service.ordersFromCsv();
    }
    public void ordersToCsv() {
        service.ordersToCsv();
    }
}
