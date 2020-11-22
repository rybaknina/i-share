package eu.senla.course.service;

import eu.senla.course.api.repository.IToolRepository;
import eu.senla.course.config.PropertyTestConfig;
import eu.senla.course.dto.tool.ToolDto;
import eu.senla.course.entity.Tool;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
class ToolServiceTest {
    private final static Logger logger = LogManager.getLogger(ToolServiceTest.class);

    @Configuration
    @Import({PropertyTestConfig.class})
    static class ContextConfiguration {
    }

    @Mock
    private IToolRepository repository;

    @InjectMocks
    private ToolService service;

    @Mock
    private OrderService orderService;

    private static List<Tool> data = new ArrayList<>();

    @Value("${tool}")
    private String toolPath;

    @BeforeAll
    static void setUp() {
        data.add(new Tool("1"));
        data.add(new Tool("2"));
    }

    @Test
    void getToolsShouldReturnListTest() {
        when(repository.getAll()).thenReturn(data);
        List<ToolDto> expected = service.getTools();
        assertEquals(data.size(), expected.size());
    }

    @Test
    void getToolsShouldThrowExceptionWhenEmptyListTest() {
        when(repository.getAll()).thenReturn(null);
        assertThrows(NullPointerException.class, () -> service.getTools());
    }

    @Test
    void setToolsShouldValidTest() {
        List<Tool> newData = new ArrayList<>();
        newData.add(new Tool("1"));
        newData.add(new Tool("2"));
        newData.add(new Tool("3"));
        lenient().doAnswer(invocation -> {
            Object arg = invocation.getArgument(0);
            assertEquals(newData, arg);
            return null;
        }).when(repository).setAll(newData);
        service.setTools(newData.stream().map(ToolDto::new).collect(Collectors.toList()));
        //verify(repository).setAll(newData);
    }

    @Test
    void addToolShouldValidTest() {
        Tool newObject = new Tool("3");
        try {
            doAnswer(invocation -> {
                Tool arg = invocation.getArgument(0);
                assertEquals(newObject.getId(), arg.getId());
                return null;
            }).when(repository).add(any(Tool.class));
            service.addTool(new ToolDto(newObject));
           // verify(repository).add(newObject);
        } catch (ServiceException | RepositoryException e) {
            logger.error(e.getMessage());
        }
    }

    @Test
    void getToolByIdShouldBeNotNullTest() {
        final int id = 1;
        given(repository.getById(id)).willReturn(data.get(id));
        assertNotNull(service.getToolById(id));
    }

    @Test
    void deleteToolShouldVerifyTest() {
        service.deleteTool(0);
        service.deleteTool(1);
        verify(repository, times(2)).delete(anyInt());
    }

    @Test
    void updateToolShouldValidTest() {
        try {
            lenient().doAnswer(invocation -> {
                Tool arg = invocation.getArgument(0);
                assertEquals(data.get(0).getId(), arg.getId());
                return null;
            }).when(repository).update(any());
            service.updateTool(new ToolDto(data.get(0)));
        } catch (RepositoryException | ServiceException e) {
            logger.error(e.getMessage());
        }
    }

    @Test
    void toolsFromCsvShouldValidTest() {
        ReflectionTestUtils.setField(service, "toolPath", toolPath);
        lenient().doAnswer(invocation -> {
            Object arg = invocation.getArgument(0);
            assertEquals(data, arg);
            return null;
        }).when(repository).setAll(data);
        try {
            service.toolsFromCsv();
        } catch (ServiceException e) {
            logger.error(e.getMessage());
        }
    }

    @Test
    void toolsToCsvShouldValidTest() {
        ReflectionTestUtils.setField(service, "toolPath", toolPath);
        when(repository.getAll()).thenReturn(data);
        service.toolsToCsv();
    }

    @AfterAll
    static void tearDown() {
        data.clear();
    }
}