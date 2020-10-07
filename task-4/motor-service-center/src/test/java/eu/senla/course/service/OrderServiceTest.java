package eu.senla.course.service;

import eu.senla.course.api.repository.IOrderRepository;
import eu.senla.course.config.PropertyTestConfig;
import eu.senla.course.dto.mechanic.MechanicDto;
import eu.senla.course.dto.order.OrderDto;
import eu.senla.course.dto.tool.ToolDto;
import eu.senla.course.entity.*;
import eu.senla.course.entity.comparator.order.ByPlannedDate;
import eu.senla.course.enums.OrderStatus;
import eu.senla.course.exception.RepositoryException;
import eu.senla.course.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
class OrderServiceTest {

    private final static Logger logger = LogManager.getLogger(OrderServiceTest.class);

    @Configuration
    @Import({PropertyTestConfig.class})
    static class ContextConfiguration {
    }

    @Mock
    private IOrderRepository repository;

    @InjectMocks
    private OrderService service;

    @Mock
    private GarageService garageService;

    @Mock
    private ToolService toolService;

    @Mock
    private MechanicService mechanicService;

    @Mock
    private SpotService spotService;

    @Value("${order}")
    private String orderPath;

    private static List<Order> data = new ArrayList<>();

    @BeforeAll
    static void setUp() {
        Garage garage = new Garage(1, "Garage One");
        Mechanic mechanic = new Mechanic(1, "Meh 1", garage);
        Spot spot = new Spot(1, garage);
        data.add(new Order(1, LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(2), mechanic, spot));
        data.get(0).setStartDate(LocalDateTime.now().plusHours(1));
        data.get(0).setCompleteDate(LocalDateTime.now().plusHours(3));
        data.add(new Order(2, LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(2), mechanic, spot));
        data.get(1).setStartDate(LocalDateTime.now().minusHours(1));
        data.get(1).setCompleteDate(LocalDateTime.now().plusHours(3));
        data.add(new Order(3, LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(2), mechanic, spot));
        data.get(2).setStartDate(LocalDateTime.now().minusHours(1));
        data.get(2).setCompleteDate(LocalDateTime.now().plusHours(3));
    }

    @Test
    void getOrdersShouldReturnListTest() {
        when(repository.getAll()).thenReturn(data);
        List<OrderDto> expected = service.getOrders();
        assertEquals(data.size(), expected.size());
    }

    @Test
    void getOrdersShouldThrowExceptionWhenEmptyListTest() {
        when(repository.getAll()).thenReturn(null);
        assertThrows(NullPointerException.class, () -> service.getOrders());
    }

    @Test
    void setOrdersShouldValidTest() {
        List<Order> newData = new ArrayList<>();
        newData.add(new Order(1, LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(2), null, null));
        newData.add(new Order(2, LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(2), null, null));
        lenient().doAnswer(invocation -> {
            Object arg = invocation.getArgument(0);
            assertEquals(newData, arg);
            return null;
        }).when(repository).setAll(newData);
        service.setOrders(newData.stream().map(OrderDto::new).collect(Collectors.toList()));
       // verify(repository).setAll(newData);
    }

    @Test
    void getOrderByIdShouldBeNotNullTest() {
        final int id = 1;
        given(repository.getById(id)).willReturn(data.get(id));
        assertNotNull(service.getOrderById(id));
    }

    @Test
    void addOrderShouldValidTest() {
        Order newObject = new Order();
        try {
            doAnswer(invocation -> {
                Order arg = invocation.getArgument(0);
                assertEquals(newObject.getStatus(), arg.getStatus());
                return null;
            }).when(repository).add(any());
            service.addOrder(new OrderDto(newObject));
            //verify(repository).add(newObject);
        } catch (ServiceException | RepositoryException e) {
            logger.error(e.getMessage());
        }
    }

    @Test
    void deleteOrderShouldVerifyTest() {
        service.deleteOrder(0);
        service.deleteOrder(1);
        verify(repository, times(2)).delete(anyInt());
    }

    @Test
    void updateOrderShouldValidTest() {
        try {
            doAnswer(invocation -> {
                Order arg = invocation.getArgument(0);
                assertEquals(data.get(0).getId(), arg.getId());
                return null;
            }).when(repository).update(any());
            service.updateOrder(new OrderDto(data.get(0)));
        } catch (RepositoryException | ServiceException e) {
            logger.error(e.getMessage());
        }
    }

    @Test
    void addToolsToOrderShouldThrowsExceptionWhenOrderIsNullTest() {
        assertThrows(ServiceException.class, () -> service.addToolsToOrder(null, new ArrayList<>()));
    }

    @Test
    void changeStatusOrderShouldSetCancelStatusTest() {
        OrderDto orderDto = new OrderDto(data.get(0));
        try {
            service.changeStatusOrder(orderDto, OrderStatus.CANCEL);
            assertEquals(OrderStatus.CANCEL, orderDto.getStatus());
        } catch (ServiceException e) {
            logger.error(e.getMessage());
        }
    }

    @Test
    void ordersForPeriodShouldReturnOneElementTest() {
        data.get(1).setStatus(OrderStatus.CLOSE);
        when(repository.getAll()).thenReturn(data);
        try {
            List<OrderDto> orders = service.ordersForPeriod(new ByPlannedDate(), OrderStatus.CLOSE, LocalDateTime.now().minusHours(2), LocalDateTime.now().plusHours(2));
            assertEquals(1, orders.size());
        } catch (ServiceException e) {
            logger.error(e.getMessage());
        }
        data.get(1).setStatus(OrderStatus.IN_PROGRESS);
    }

    @Test
    void listCurrentOrdersValidTest() {
        when(repository.getAll()).thenReturn(data);
        try {
            service.listCurrentOrders(new ByPlannedDate());
        } catch (ServiceException e) {
            logger.error(e.getMessage());
        }
    }

    @Test
    void changeStartDateOrdersShouldChangeDateTest() {
        when(repository.getAll()).thenReturn(data.subList(2,2));
        LocalDateTime dateTime = data.get(2).getStartDate();
        try {
            service.changeStartDateOrders(2);
            long hours = ChronoUnit.HOURS.between(dateTime, data.get(2).getStartDate());
            assertEquals(2, hours);
        } catch (ServiceException e) {
            logger.error(e.getMessage());
        }
    }

    @Test
    void listOrdersValidTest() {
        when(repository.getAll()).thenReturn(data);
        try {
            service.listOrders(new ByPlannedDate());
        } catch (ServiceException e) {
            logger.error(e.getMessage());
        }
    }

    @Test
    void mechanicOrderShouldReturnOrderTest() {
        when(repository.getAll()).thenReturn(data.subList(1,1));
        try {
            assertEquals(1, service.mechanicOrder(new MechanicDto(data.get(1).getMechanic())).getId());
        } catch (ServiceException e) {
            logger.error(e.getMessage());
        }
    }

    @Test
    void orderMechanicShouldReturnMechanicTest() {
        Mechanic mechanic = data.get(0).getMechanic();
        when(repository.getAll()).thenReturn(data);
        try {
            assertEquals(mechanic.getId(), service.orderMechanic(new OrderDto(data.get(0))).getId());
        } catch (ServiceException e) {
            logger.error(e.getMessage());
        }
    }

    @Test
    void nextAvailableDateShouldReturnNowTest() {
        LocalDate endDate = LocalDate.now().plusDays(3);
        when(repository.getAll()).thenReturn(data);

        try {
            when(garageService.numberAvailableSpots(any(LocalDateTime.class), anyList())).thenReturn(3);
            assertEquals(LocalDate.now(), service.nextAvailableDate(endDate).toLocalDate());
        } catch (ServiceException e) {
            logger.error(e.getMessage());
        }
    }

    @Test
    void billShouldSetPriceInOrderTest() {
        OrderDto order = new OrderDto(data.get(0));
        List<ToolDto> tools = new ArrayList<>();
        tools.add(new ToolDto(new Tool(1, "1", 2, new BigDecimal(2.5), data.get(0))));
        given(toolService.getTools()).willReturn(tools);
        try {
            service.bill(order);
            BigDecimal amount = new BigDecimal(5);
            assertTrue(amount.compareTo(order.getPrice()) == 0);
        } catch (ServiceException e) {
            logger.error(e.getMessage());
        }
    }

    @Test
    void ordersFromCsvShouldValidTest() {
        ReflectionTestUtils.setField(service, "orderPath", orderPath);
        lenient().doAnswer(invocation -> {
            Object arg = invocation.getArgument(0);
            assertEquals(data, arg);
            return null;
        }).when(repository).setAll(data);
        try {
            service.ordersFromCsv();
        } catch (ServiceException e) {
            logger.error(e.getMessage());
        }
    }

    @Test
    void ordersToCsvShouldValidTest() {
        ReflectionTestUtils.setField(service, "orderPath", orderPath);
        when(repository.getAll()).thenReturn(data);
        service.ordersToCsv();
    }

    @AfterAll
    static void tearDown() {
        data.clear();
    }
}