package eu.senla.course.service;

import eu.senla.course.api.repository.IGarageRepository;
import eu.senla.course.api.service.IGarageService;
import eu.senla.course.config.PropertyTestConfig;
import eu.senla.course.controller.MechanicController;
import eu.senla.course.controller.SpotController;
import eu.senla.course.entity.Garage;
import eu.senla.course.exception.RepositoryException;
import eu.senla.course.exception.ServiceException;
import eu.senla.course.util.TestUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.JavaTimeConversionPattern;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
class GarageServiceTest {
    private final static Logger logger = LogManager.getLogger(GarageServiceTest.class);

    @Configuration
    @Import({PropertyTestConfig.class})
    static class ContextConfiguration {
        @Bean
        public IGarageService getGarageService() {
            return new GarageService();
        }

        @Bean
        public IGarageRepository getGarageRepository() {
            return Mockito.mock(IGarageRepository.class);
        }
    }

    @Autowired
    private IGarageRepository repository;

    @Autowired
    private IGarageService service;

    private static List<Garage> data = new ArrayList<>();

    @BeforeAll
    static void setUp() {
        data.add(new Garage("1"));
        data.add(new Garage("2"));
    }

    @Test
    void getGaragesShouldReturnListTest() {
        when(repository.getAll()).thenReturn(data);
        List<Garage> expected = service.getGarages();
        assertEquals(data.size(), expected.size());
    }

    @Test
    void getGaragesShouldThrowExceptionWhenEmptyListTest() {
        when(repository.getAll()).thenReturn(null);
        assertNull(service.getGarages());
    }

    @Test
    void setGaragesShouldValidTest() {
        List<Garage> newData = new ArrayList<>();
        newData.add(new Garage("1"));
        newData.add(new Garage("2"));
        newData.add(new Garage("3"));
        doAnswer(invocation -> {
            Object arg = invocation.getArgument(0);
            assertEquals(newData, arg);
            return null;
        }).when(repository).setAll(newData);
        service.setGarages(newData);
        verify(repository).setAll(newData);
    }

    @Test
    void setGaragesShouldNothingHappensWhenAddNullTest() {
        service.setGarages(null);
    }

    @Test
    void addGarageShouldValidTest() throws RepositoryException, ServiceException {
        Garage newObject = new Garage("3");
        SpotController controller = mock(SpotController.class);
        TestUtil.setMock(controller, SpotController.class);
        when(controller.spotsInGarage(any())).thenReturn(new ArrayList<>());
        doAnswer(invocation -> {
            Object arg = invocation.getArgument(0);
            assertEquals(newObject, arg);
            return null;
        }).when(repository).add(any(Garage.class));
        service.addGarage(newObject);
        verify(repository).add(newObject);
        TestUtil.resetSingleton(SpotController.class);
    }

    @Test
    void addGarageShouldThrowExceptionWhenAddNullTest() {
        assertThrows(NullPointerException.class, () -> service.addGarage(null));
    }

    @Test
    void updateGarageShouldValidTest() {
        try {
            doAnswer(invocation -> {
                Object arg = invocation.getArgument(0);
                assertEquals(data.get(0), arg);
                return null;
            }).when(repository).update(any());
            service.updateGarage(data.get(0));
        } catch (RepositoryException | ServiceException e) {
            logger.error(e.getMessage());
        }
    }

    @Test
    void deleteGarageShouldVerifyTest() {
        service.deleteGarage(data.get(0).getId());
        verify(repository, times(1)).delete(anyInt());
    }

    @Test
    void getGarageByIdShouldBeNotNullTest() {
        final int id = 1;
        given(repository.getById(id)).willReturn(data.get(id));
        assertNotNull(service.getGarageById(id));
    }

    @Test
    void lengthAllSpotsShouldBeReturnZeroTest() {
        when(repository.getAll()).thenReturn(data);
        assertEquals(0, service.lengthAllSpots());
    }

    @ParameterizedTest
    @ValueSource(strings = {"15.09.2020 10:00"})
    void listAvailableSpotsReturnZeroTest(@JavaTimeConversionPattern(value = "dd.MM.yyyy HH:mm") LocalDateTime date) {
        when(repository.getAll()).thenReturn(data);
        try {
            assertEquals(0, service.listAvailableSpots(date, new ArrayList<>()).size());
        } catch (ServiceException e) {
            logger.error(e.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"15.09.2020 10:00"})
    void numberAvailableSpotsReturnZeroTest(@JavaTimeConversionPattern(value = "dd.MM.yyyy HH:mm") LocalDateTime date) {
        try {
            assertEquals(0, service.numberAvailableSpots(date, new ArrayList<>()));
        } catch (ServiceException e) {
            logger.error(e.getMessage());
        }
    }

    @Test
    void garagesFromCsvValidTest() {
        given(repository.getById(0)).willReturn(data.get(0));
        doAnswer(invocation -> {
            Object arg = invocation.getArgument(0);
            assertEquals(data, arg);
            return null;
        }).when(repository).setAll(data);
        try {
            service.garagesFromCsv();
        } catch (ServiceException e) {
            logger.error(e.getMessage());
        }
    }

    @Test
    void garagesToCsvShouldValidTest() {
        SpotController spotController = mock(SpotController.class);
        TestUtil.setMock(spotController, SpotController.class);
        given(spotController.getSpots()).willReturn(new ArrayList<>());

        MechanicController mechanicController = mock(MechanicController.class);
        TestUtil.setMock(mechanicController, MechanicController.class);
        given(mechanicController.getMechanics()).willReturn(new ArrayList<>());

        when(repository.getAll()).thenReturn(data);
        service.garagesToCsv();

        TestUtil.resetSingleton(SpotController.class);
        TestUtil.resetSingleton(MechanicController.class);
    }

    @AfterAll
    static void tearDown() {
        data.clear();
    }
}