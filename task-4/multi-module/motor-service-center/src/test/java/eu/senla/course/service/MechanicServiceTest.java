package eu.senla.course.service;

import eu.senla.course.api.repository.IMechanicRepository;
import eu.senla.course.config.PropertyTestConfig;
import eu.senla.course.dto.mechanic.MechanicDto;
import eu.senla.course.entity.Garage;
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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static junit.framework.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
class MechanicServiceTest {
    private final static Logger logger = LogManager.getLogger(MechanicServiceTest.class);

    @Configuration
    @Import({PropertyTestConfig.class})
    static class ContextConfiguration {
    }

    @Mock
    private IMechanicRepository repository;

    @InjectMocks
    private MechanicService service;

    @Mock
    private OrderService orderService;

    @Mock
    private GarageService garageService;

    @Value("${mechanic}")
    private String mechanicPath;

    private static List<Mechanic> data = new ArrayList<>();

    @BeforeAll
    static void setUp() {
        data.add(new Mechanic("1", new Garage("1")));
        data.add(new Mechanic("2", new Garage("2")));
    }

    @Test
    void addMechanicShouldValidTest() throws RepositoryException, ServiceException {
        Mechanic newObject = new Mechanic("3", new Garage("1"));
        lenient().doAnswer(invocation -> {
            Mechanic arg = invocation.getArgument(0);
            assertEquals(newObject.getId(), arg.getId());
            return null;
        }).when(repository).add(any(Mechanic.class));
        service.addMechanic(new MechanicDto(newObject));
        //verify(repository).add(newObject);
    }

    @Test
    void getMechanicsShouldReturnListTest() {
        when(repository.getAll()).thenReturn(data);
        List<MechanicDto> expected = service.getMechanics();
        assertEquals(data.size(), expected.size());
    }

    @Test
    void getMechanicsShouldThrowExceptionWhenEmptyListTest() {
        when(repository.getAll()).thenReturn(null);
        assertThrows(NullPointerException.class, () -> service.getMechanics());
    }

    @Test
    void setMechanicsShouldValidTest() {
        List<Mechanic> newData = new ArrayList<>();
        newData.add(new Mechanic("1"));
        newData.add(new Mechanic("2"));
        newData.add(new Mechanic("3"));
        lenient().doAnswer(invocation -> {
            Object arg = invocation.getArgument(0);
            assertEquals(newData, arg);
            return null;
        }).when(repository).setAll(newData);
        service.setMechanics(newData.stream().map(MechanicDto::new).collect(Collectors.toList()));
        //verify(repository).setAll(newData);
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
        Mechanic newObject = new Mechanic("4");
        MechanicDto mechanicDto = new MechanicDto(newObject);
        try {
            doAnswer(invocation -> {
                Mechanic arg = invocation.getArgument(0);
                assertEquals(newObject.getId(), arg.getId());
                return null;
            }).when(repository).update(any());
            service.updateMechanic(mechanicDto);
        } catch (RepositoryException | ServiceException e) {
            logger.error(e.getMessage());
        }
    }

    @Test
    void firstFreeMechanicShouldFindFirstInDataTest() {
        when(repository.getAll()).thenReturn(data);
        try {
            MechanicDto mechanic = service.firstFreeMechanic();
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
        ReflectionTestUtils.setField(service, "mechanicPath", mechanicPath);
        given(repository.getById(0)).willReturn(data.get(0));
        lenient().doAnswer(invocation -> {
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
        ReflectionTestUtils.setField(service, "mechanicPath", mechanicPath);
        given(orderService.getOrders()).willReturn(new ArrayList<>());

        when(repository.getAll()).thenReturn(data);
        service.mechanicsToCsv();
    }

    @AfterAll
    static void tearDown() {
        data.clear();
    }
}