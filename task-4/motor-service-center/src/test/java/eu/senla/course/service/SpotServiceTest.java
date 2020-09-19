package eu.senla.course.service;

import eu.senla.course.api.repository.ISpotRepository;
import eu.senla.course.api.service.ISpotService;
import eu.senla.course.config.PropertyTestConfig;
import eu.senla.course.controller.SpotController;
import eu.senla.course.entity.Garage;
import eu.senla.course.entity.Spot;
import eu.senla.course.exception.RepositoryException;
import eu.senla.course.exception.ServiceException;
import eu.senla.course.util.TestUtil;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
class SpotServiceTest {
    private final static Logger logger = LogManager.getLogger(SpotServiceTest.class);

    @Configuration
    @Import({PropertyTestConfig.class})
    static class ContextConfiguration {
        @Bean
        public ISpotService getMechanicService() {
            return new SpotService();
        }

        @Bean
        public ISpotRepository getMechanicRepository() {
            return Mockito.mock(ISpotRepository.class);
        }
    }

    @Autowired
    private ISpotRepository repository;

    @Autowired
    private ISpotService service;

    private static List<Spot> data = new ArrayList<>();

    @BeforeAll
    static void setUp() {
        Garage garage = new Garage(1,"1");
        data.add(new Spot(garage));
        data.add(new Spot(new Garage("2")));
        data.add(new Spot(garage));
    }

    @Test
    void getSpotsShouldReturnListTest() {
        when(repository.getAll()).thenReturn(data);
        List<Spot> expected = service.getSpots();
        assertEquals(data.size(), expected.size());
    }

    @Test
    void getSpotsShouldThrowExceptionWhenEmptyListTest() {
        when(repository.getAll()).thenReturn(null);
        assertNull(service.getSpots());
    }

    @Test
    void setSpotsShouldValidTest() {
        List<Spot> newData = new ArrayList<>();
        newData.add(new Spot(new Garage("4")));
        newData.add(new Spot(new Garage("2")));
        newData.add(new Spot(new Garage("3")));
        doAnswer(invocation -> {
            Object arg = invocation.getArgument(0);
            assertEquals(newData, arg);
            return null;
        }).when(repository).setAll(newData);
        service.setSpots(newData);
        verify(repository).setAll(newData);
    }

    @Test
    void addSpotShouldValidTest() {
        Spot newObject = new Spot();
        try {
            doAnswer(invocation -> {
                Object arg = invocation.getArgument(0);
                assertEquals(newObject, arg);
                return null;
            }).when(repository).add(any(Spot.class));
            service.addSpot(newObject);
            verify(repository).add(newObject);
        } catch (ServiceException | RepositoryException e) {
            logger.error(e.getMessage());
        }
    }

    @Test
    void getSpotByIdShouldBeNotNullTest() {
        final int id = 1;
        given(repository.getById(id)).willReturn(data.get(id));
        assertNotNull(service.getSpotById(id));
    }

    @Test
    void deleteSpotShouldVerifyTest() {
        service.deleteSpot(0);
        service.deleteSpot(1);
        verify(repository, times(2)).delete(anyInt());
    }

    @Test
    void updateSpotShouldValidTest() {
        try {
            doAnswer(invocation -> {
                Object arg = invocation.getArgument(0);
                assertEquals(data.get(0), arg);
                return null;
            }).when(repository).update(any());
            service.updateSpot(data.get(0));
        } catch (RepositoryException | ServiceException e) {
            logger.error(e.getMessage());
        }
    }

    @Test
    void spotsInGarageShouldReturnNumberOfSpotsTest() {
        Garage garage = data.get(0).getGarage();
        SpotController spotController = mock(SpotController.class);
        TestUtil.setMock(spotController, SpotController.class);
        given(spotController.getSpots()).willReturn(data);
        assertEquals(2, service.spotsInGarage(garage).size());
        TestUtil.resetSingleton(SpotController.class);
    }

    @Test
    void spotsFromCsvShouldValidTest() {
        given(repository.getById(0)).willReturn(data.get(0));
        doAnswer(invocation -> {
            Object arg = invocation.getArgument(0);
            assertEquals(data, arg);
            return null;
        }).when(repository).setAll(data);
        try {
            service.spotsFromCsv();
        } catch (ServiceException e) {
            logger.error(e.getMessage());
        }
    }

    @Test
    void spotsToCsvShouldValidTest() {
        when(repository.getAll()).thenReturn(data);
        service.spotsToCsv();
    }

    @AfterAll
    static void tearDown() {
        data.clear();
    }
}