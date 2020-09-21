package eu.senla.course.service;

import eu.senla.course.api.repository.IMechanicRepository;
import eu.senla.course.api.service.IGarageService;
import eu.senla.course.api.service.IMechanicService;
import eu.senla.course.api.service.IOrderService;
import eu.senla.course.config.PropertyTestConfig;
import eu.senla.course.entity.Mechanic;
import eu.senla.course.entity.comparator.mechanic.ByAlphabet;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
class MechanicServiceTest {
    private final static Logger logger = LogManager.getLogger(MechanicServiceTest.class);

    @Configuration
    @Import({PropertyTestConfig.class})
    static class ContextConfiguration {
        @Bean
        public IMechanicService getMechanicService() {
            return new MechanicService();
        }

        @Bean
        public IMechanicRepository getMechanicRepository() {
            return Mockito.mock(IMechanicRepository.class);
        }

        @Bean
        public IOrderService getOrderService() {
            return Mockito.mock(IOrderService.class);
        }

        @Bean
        public IGarageService getGarageService() {
            return Mockito.mock(IGarageService.class);
        }
    }

    @Autowired
    private IMechanicRepository repository;

    @Autowired
    private IMechanicService service;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IGarageService garageService;

    private static List<Mechanic> data = new ArrayList<>();

    @BeforeAll
    static void setUp() {
        data.add(new Mechanic("1"));
        data.add(new Mechanic("2"));
    }

    @Test
    void addMechanicShouldValidTest() throws RepositoryException, ServiceException {
        Mechanic newObject = new Mechanic("3");
        doAnswer(invocation -> {
            Object arg = invocation.getArgument(0);
            assertEquals(newObject, arg);
            return null;
        }).when(repository).add(any(Mechanic.class));
        service.addMechanic(newObject);
        verify(repository).add(newObject);
    }

    @Test
    void getMechanicsShouldReturnListTest() {
        when(repository.getAll()).thenReturn(data);
        List<Mechanic> expected = service.getMechanics();
        assertEquals(data.size(), expected.size());
    }

    @Test
    void getMechanicsShouldThrowExceptionWhenEmptyListTest() {
        when(repository.getAll()).thenReturn(null);
        assertNull(service.getMechanics());
    }

    @Test
    void setMechanicsShouldValidTest() {
        List<Mechanic> newData = new ArrayList<>();
        newData.add(new Mechanic("1"));
        newData.add(new Mechanic("2"));
        newData.add(new Mechanic("3"));
        doAnswer(invocation -> {
            Object arg = invocation.getArgument(0);
            assertEquals(newData, arg);
            return null;
        }).when(repository).setAll(newData);
        service.setMechanics(newData);
        verify(repository).setAll(newData);
    }

    @Test
    void deleteMechanicShouldVerifyTest() {
        service.deleteMechanic(data.get(0).getId());
        verify(repository, times(1)).delete(anyInt());
    }

    @Test
    void getMechanicByIdShouldBeNotNullTest() {
        final int id = 1;
        given(repository.getById(id)).willReturn(data.get(id));
        assertNotNull(service.getMechanicById(id));
    }

    @Test
    void updateMechanicShouldValidTest() {
        try {
            doAnswer(invocation -> {
                Object arg = invocation.getArgument(0);
                assertEquals(data.get(0), arg);
                return null;
            }).when(repository).update(any());
            service.updateMechanic(data.get(0));
        } catch (RepositoryException | ServiceException e) {
            logger.error(e.getMessage());
        }
    }

    @Test
    void firstFreeMechanicShouldFindFirstInDataTest() {
        when(repository.getAll()).thenReturn(data);
        try {
            Mechanic mechanic = service.firstFreeMechanic();
            assertEquals(mechanic.getId(), data.get(0).getId());
        } catch (ServiceException e) {
            logger.error(e.getMessage());
        }
    }

    @Test
    void sortMechanicsByValidTest() {
        when(repository.getAll()).thenReturn(data);
        try {
            service.sortMechanicsBy(new ByAlphabet());
        } catch (ServiceException e) {
            logger.error(e.getMessage());
        }
    }

    @Test
    void mechanicsFromCsvShouldValidTest() {
        given(repository.getById(0)).willReturn(data.get(0));
        doAnswer(invocation -> {
            Object arg = invocation.getArgument(0);
            assertEquals(data, arg);
            return null;
        }).when(repository).setAll(data);
        try {
            service.mechanicsFromCsv();
        } catch (ServiceException e) {
            logger.error(e.getMessage());
        }
    }

    @Test
    void mechanicsToCsvShouldValidTest() {
        given(orderService.getOrders()).willReturn(new ArrayList<>());

        when(repository.getAll()).thenReturn(data);
        service.mechanicsToCsv();
    }

    @AfterAll
    static void tearDown() {
        data.clear();
    }
}