package eu.senla.course.service;

import eu.senla.course.api.repository.ISpotRepository;
import eu.senla.course.config.PropertyTestConfig;
import eu.senla.course.dto.garage.GarageDto;
import eu.senla.course.dto.spot.SpotDto;
import eu.senla.course.entity.Garage;
import eu.senla.course.entity.Spot;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
class SpotServiceTest {
    private final static Logger logger = LogManager.getLogger(SpotServiceTest.class);

    @Configuration
    @Import({PropertyTestConfig.class})
    static class ContextConfiguration {
    }

    @Mock
    private ISpotRepository repository;

    @InjectMocks
    private SpotService service;

    @Mock
    private GarageService garageService;

    private static List<Spot> data = new ArrayList<>();

    @Value("${spot}")
    private String spotPath;

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
        List<SpotDto> expected = service.getSpots();
        assertEquals(data.size(), expected.size());
    }

    @Test
    void getSpotsShouldThrowExceptionWhenEmptyListTest() {
        when(repository.getAll()).thenReturn(null);
        assertThrows(NullPointerException.class, () -> service.getSpots());
    }

    @Test
    void setSpotsShouldValidTest() {
        List<Spot> newData = new ArrayList<>();
        newData.add(new Spot());
        newData.add(new Spot());
        newData.add(new Spot());
        lenient().doAnswer(invocation -> {
            Object arg = invocation.getArgument(0);
            assertEquals(newData, arg);
            return null;
        }).when(repository).setAll(newData);
        service.setSpots(newData.stream().map(SpotDto::new).collect(Collectors.toList()));
        //verify(repository).setAll(newData);
    }

    @Test
    void addSpotShouldValidTest() {
        Spot newObject = new Spot();
        try {
            doAnswer(invocation -> {
                Spot arg = invocation.getArgument(0);
                assertEquals(newObject.getId(), arg.getId());
                return null;
            }).when(repository).add(any());
            service.addSpot(new SpotDto(newObject));
           // verify(repository).add(newObject);
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
                Spot arg = invocation.getArgument(0);
                assertEquals(data.get(0).getId(), arg.getId());
                return null;
            }).when(repository).update(any());
            service.updateSpot(new SpotDto(data.get(0)));
        } catch (RepositoryException | ServiceException e) {
            logger.error(e.getMessage());
        }
    }

    @Test
    void spotsInGarageShouldReturnNumberOfSpotsTest() {
        GarageDto garage = new GarageDto(data.get(0).getGarage());
        when(repository.getAll()).thenReturn(data);
        assertEquals(2, service.spotsInGarage(garage).size());
    }

    @Test
    void spotsFromCsvShouldValidTest() {
        ReflectionTestUtils.setField(service, "spotPath", spotPath);
        lenient().doAnswer(invocation -> {
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
        ReflectionTestUtils.setField(service, "spotPath", spotPath);
        when(repository.getAll()).thenReturn(data);
        service.spotsToCsv();
    }

    @AfterAll
    static void tearDown() {
        data.clear();
    }
}