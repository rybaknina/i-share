package eu.senla.course.service;

import eu.senla.course.api.repository.IOrderRepository;
import eu.senla.course.api.service.*;
import eu.senla.course.config.PropertyTestConfig;
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
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
class OrderServiceTest {

    private final static Logger logger = LogManager.getLogger(OrderServiceTest.class);

    @Configuration
    @Import({PropertyTestConfig.class})
    static class ContextConfiguration {
        @Bean
        public IOrderService getMechanicService() {
            return new OrderService();
        }

        @Bean
        public IOrderRepository getMechanicRepository() {
            return Mockito.mock(IOrderRepository.class);
        }

        @Bean
        public IGarageService setGarageService() {
            return Mockito.mock(IGarageService.class);
        }

        @Bean
        public IMechanicService setMechanicService() {
            return Mockito.mock(IMechanicService.class);
        }

        @Bean
        public IToolService setToolService() {
            return Mockito.mock(IToolService.class);
        }

        @Bean
        public ISpotService setSpotService() {
            return Mockito.mock(ISpotService.class);
        }
    }



    @Autowired
    private IOrderRepository repository;

    @Autowired
    private IOrderService service;

    @Autowired
    private IGarageService garageService;

    @Autowired
    private IToolService toolService;

    @Autowired
    private IMechanicService mechanicService;

    @Autowired
    private ISpotService spotService;

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
        List<Order> expected = service.getOrders();
        assertEquals(data.size(), expected.size());
    }

    @Test
    void getOrdersShouldThrowExceptionWhenEmptyListTest() {
        when(repository.getAll()).thenReturn(null);
        assertNull(service.getOrders());
    }

    @Test
    void setOrdersShouldValidTest() {
        List<Order> newData = new ArrayList<>();
        newData.add(new Order(1, LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(2), null, null));
        newData.add(new Order(2, LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(2), null, null));
        doAnswer(invocation -> {
            Object arg = invocation.getArgument(0);
            assertEquals(newData, arg);
            return null;
        }).when(repository).setAll(newData);
        service.setOrders(newData);
        verify(repository).setAll(newData);
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
                Object arg = invocation.getArgument(0);
                assertEquals(newObject, arg);
                return null;
            }).when(repository).add(any(Order.class));
            service.addOrder(newObject);
            verify(repository).add(newObject);
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
                Object arg = invocation.getArgument(0);
                assertEquals(data.get(0), arg);
                return null;
            }).when(repository).update(any());
            service.updateOrder(data.get(0));
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
        when(repository.getAll()).thenReturn(data);
        try {
            service.changeStatusOrder(data.get(0), OrderStatus.CANCEL);
            assertEquals(OrderStatus.CANCEL, data.get(0).getStatus());
        } catch (ServiceException e) {
            logger.error(e.getMessage());
        }
    }

    @Test
    void ordersForPeriodShouldReturnOneElementTest() {
        data.get(1).setStatus(OrderStatus.CLOSE);
        when(repository.getAll()).thenReturn(data);
        try {
            List<Order> orders = service.ordersForPeriod(new ByPlannedDate(), OrderStatus.CLOSE, LocalDateTime.now().minusHours(2), LocalDateTime.now().plusHours(2));
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
            assertEquals(1, service.mechanicOrder(data.get(1).getMechanic()).getId());
        } catch (ServiceException e) {
            logger.error(e.getMessage());
        }
    }

    @Test
    void orderMechanicShouldReturnMechanicTest() {
        Mechanic mechanic = data.get(0).getMechanic();
        when(repository.getAll()).thenReturn(data);
        try {
            assertEquals(mechanic.getId(), service.orderMechanic(data.get(0)).getId());
        } catch (ServiceException e) {
            logger.error(e.getMessage());
        }
    }

    @Test
    void nextAvailableDateShouldReturnNowTest() {
        LocalDate endDate = LocalDate.now().plusDays(1);
        when(repository.getAll()).thenReturn(data);
        try {
            given(garageService.numberAvailableSpots(any(LocalDateTime.class), eq(data))).willReturn(2);
            assertEquals(LocalDate.now(), service.nextAvailableDate(endDate).toLocalDate());
        } catch (ServiceException e) {
            logger.error(e.getMessage());
        }
    }

    @Test
    void billShouldSetPriceInOrderTest() {
        Order order = data.get(0);
        List<Tool> tools = new ArrayList<>();
        tools.add(new Tool(1, "1", 2, new BigDecimal(2.5), order));
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
        given(repository.getById(0)).willReturn(data.get(0));
        doAnswer(invocation -> {
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
        when(repository.getAll()).thenReturn(data);
        service.ordersToCsv();
    }

    @AfterAll
    static void tearDown() {
        data.clear();
    }
}