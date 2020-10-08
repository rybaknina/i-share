package eu.senla.course.controller;

import eu.senla.course.api.service.IOrderService;
import eu.senla.course.dto.mechanic.MechanicDto;
import eu.senla.course.dto.order.OrderDto;
import eu.senla.course.dto.tool.ToolDto;
import eu.senla.course.entity.comparator.order.ByCompleteDate;
import eu.senla.course.entity.comparator.order.ByPlannedDate;
import eu.senla.course.entity.comparator.order.ByPrice;
import eu.senla.course.entity.comparator.order.ByRequestDate;
import eu.senla.course.enums.OrderComparator;
import eu.senla.course.enums.OrderStatus;
import eu.senla.course.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
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

    @PutMapping("/change_order_status/{status}")
    public void changeStatusOrder(@RequestBody OrderDto orderDto, @PathVariable OrderStatus status) throws ServiceException {
        service.changeStatusOrder(orderDto, status);
    }

    public boolean isShiftTime() {
        return service.isShiftTime();
    }

    @GetMapping("/sort_orders/{comparator}")
    public List<OrderDto> listOrders(@PathVariable String comparator) throws ServiceException {
        Comparator<OrderDto> dtoComparator = getOrderComparator(comparator);
        return service.listOrders(dtoComparator);
    }

    private Comparator<OrderDto> getOrderComparator(String comparator) {
        comparator = comparator.toUpperCase();
        OrderComparator orderComparator;
        try {
            orderComparator = OrderComparator.valueOf(comparator);
        } catch (IllegalArgumentException ex) {
            orderComparator = OrderComparator.BY_COMPLETE_DATE;
        }
        Comparator<OrderDto> dtoComparator;
        switch (orderComparator) {
            case BY_COMPLETE_DATE:
                dtoComparator = new ByCompleteDate();
                break;
            case BY_PLANNED_DATE:
                dtoComparator = new ByPlannedDate();
                break;
            case BY_PRICE:
                dtoComparator = new ByPrice();
                break;
            case BY_REQUEST_DATE:
                dtoComparator = new ByRequestDate();
                break;
            default:
                dtoComparator = new ByCompleteDate();
        }
        return dtoComparator;
    }

    @PutMapping("/change_orders_start/{hours}")
    public void changeStartDateOrders(@PathVariable int hours) throws ServiceException {
        service.changeStartDateOrders(hours);
    }

    @GetMapping("/next_free_date/{endDate}")
    public LocalDateTime nextAvailableDate(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")  LocalDate endDate) throws ServiceException {
        return service.nextAvailableDate(endDate);
    }

    @GetMapping("/order_by_mechanic")
    public OrderDto mechanicOrder(@RequestBody MechanicDto mechanicDto) throws ServiceException {
        return service.mechanicOrder(mechanicDto);
    }

    @GetMapping("/mechanic_by_order")
    public MechanicDto orderMechanic(@RequestBody OrderDto orderDto) throws ServiceException {
        return service.orderMechanic(orderDto);
    }

    @GetMapping("/orders_for_period/{comparator}/{status}/{startDate}/{endDate}")
    public List<OrderDto> ordersForPeriod(@PathVariable String comparator, @PathVariable OrderStatus status, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startDate, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")  LocalDateTime endDate) throws ServiceException {
        return service.ordersForPeriod(getOrderComparator(comparator), status, startDate, endDate);
    }

    @GetMapping("/sort_current_orders/{comparator}")
    public List<OrderDto> listCurrentOrders(@PathVariable String comparator) throws ServiceException {
        Comparator<OrderDto> dtoComparator = getOrderComparator(comparator);
        return service.listCurrentOrders(dtoComparator);
    }

    @PutMapping("/bill")
    public BigDecimal bill(@RequestBody OrderDto orderDto) throws ServiceException {
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
