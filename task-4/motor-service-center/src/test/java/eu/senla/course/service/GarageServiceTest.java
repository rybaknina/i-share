package eu.senla.course.service;

import eu.senla.course.api.repository.IGarageRepository;
import eu.senla.course.config.PropertyTestConfig;
import eu.senla.course.dto.garage.GarageDto;
import eu.senla.course.entity.Garage;
import eu.senla.course.exception.RepositoryException;
import eu.senla.course.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.JavaTimeConversionPattern;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
class GarageServiceTest {
    private final static Logger logger = LogManager.getLogger(GarageServiceTest.class);
    @Configuration
    @Import({PropertyTestConfig.class})
    static class ContextConfiguration {
    }

    @Mock
    private IGarageRepository repository;

    @InjectMocks
    private GarageService service;

    @Mock
    private SpotService spotService;

    @Mock
    private MechanicService mechanicService;

    @Value("${garage}")
    private String garagePath;

    private static List<Garage> data = new ArrayList<>();

    @BeforeAll
    static void setUp() {
        data.add(new Garage("1"));
        data.add(new Garage("2"));
    }

    @Test
    void getGaragesShouldReturnListTest() {
        when(repository.getAll()).thenReturn(data);
        List<GarageDto> expected = service.getGarages();
        assertEquals(data.size(), expected.size());
    }

    @Test
    void getGaragesShouldThrowExceptionWhenEmptyListTest() {
        when(repository.getAll()).thenReturn(null);
        assertThrows(NullPointerException.class, () -> service.getGarages());
    }

    @Test
    void setGaragesShouldValidTest() {
        List<Garage> newData = new ArrayList<>();
        newData.add(new Garage("1"));
        newData.add(new Garage("2"));
        newData.add(new Garage("3"));
        lenient().doAnswer(invocation -> {
            Object arg = invocation.getArgument(0);
            assertEquals(newData, arg);
            return null;
        }).when(repository).setAll(newData);
        service.setGarages(newData.stream().map(GarageDto::new).collect(Collectors.toList()));
        //verify(repository).setAll(newData);
    }

    @Test
    void setGaragesShouldThrowExceptionHappensWhenAddNullTest() {
        assertThrows(NullPointerException.class, () -> service.setGarages(null));
    }

    @Test
    void addGarageShouldValidTest() throws RepositoryException, ServiceException {
        Garage newObject = new Garage("3");
        doAnswer(invocation -> {
            Garage arg = invocation.getArgument(0);
            assertEquals(newObject.getId(), arg.getId());
            return null;
        }).when(repository).add(any());
        service.addGarage(new GarageDto(newObject));
       // verify(repository).add(newObject);
    }

    @Test
    void addGarageShouldThrowExceptionWhenAddNullTest() {
        assertThrows(NullPointerException.class, () -> service.addGarage(null));
    }

    @Test
    void updateGarageShouldValidTest() {
        Garage garage = data.get(0);
        try {
            doAnswer(invocation -> {
                Garage arg = invocation.getArgument(0);
                assertEquals(garage.getId(), arg.getId());
                return null;
            }).when(repository).update(any());
            service.updateGarage(new GarageDto(garage));
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
        ReflectionTestUtils.setField(service, "garagePath", garagePath);
        given(repository.getById(0)).willReturn(data.get(0));
        lenient().doAnswer(invocation -> {
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
        given(spotService.getSpots()).willReturn(new ArrayList<>());
        given(mechanicService.getMechanics()).willReturn(new ArrayList<>());

        when(repository.getAll()).thenReturn(data);
        ReflectionTestUtils.setField(service, "garagePath", garagePath);
        service.garagesToCsv();
    }

    @AfterAll
    static void tearDown() {
        data.clear();
    }
}