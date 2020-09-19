package eu.senla.course.service;

import eu.senla.course.api.repository.IToolRepository;
import eu.senla.course.api.service.IToolService;
import eu.senla.course.config.PropertyTestConfig;
import eu.senla.course.entity.Tool;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
class ToolServiceTest {
    private final static Logger logger = LogManager.getLogger(ToolServiceTest.class);

    @Configuration
    @Import({PropertyTestConfig.class})
    static class ContextConfiguration {
        @Bean
        public IToolService getToolService() {
            return new ToolService();
        }

        @Bean
        public IToolRepository getToolRepository() {
            return Mockito.mock(IToolRepository.class);
        }
    }

    @Autowired
    private IToolRepository repository;

    @Autowired
    private IToolService service;

    private static List<Tool> data = new ArrayList<>();

    @BeforeAll
    static void setUp() {
        data.add(new Tool("1"));
        data.add(new Tool("2"));
    }

    @Test
    void getToolsShouldReturnListTest() {
        when(repository.getAll()).thenReturn(data);
        List<Tool> expected = service.getTools();
        assertEquals(data.size(), expected.size());
    }

    @Test
    void getToolsShouldThrowExceptionWhenEmptyListTest() {
        when(repository.getAll()).thenReturn(null);
        assertNull(service.getTools());
    }

    @Test
    void setToolsShouldValidTest() {
        List<Tool> newData = new ArrayList<>();
        newData.add(new Tool("1"));
        newData.add(new Tool("2"));
        newData.add(new Tool("3"));
        doAnswer(invocation -> {
            Object arg = invocation.getArgument(0);
            assertEquals(newData, arg);
            return null;
        }).when(repository).setAll(newData);
        service.setTools(newData);
        verify(repository).setAll(newData);
    }

    @Test
    void addToolShouldValidTest() {
        Tool newObject = new Tool("3");
        try {
            doAnswer(invocation -> {
                Object arg = invocation.getArgument(0);
                assertEquals(newObject, arg);
                return null;
            }).when(repository).add(any(Tool.class));
            service.addTool(newObject);
            verify(repository).add(newObject);
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
            doAnswer(invocation -> {
                Object arg = invocation.getArgument(0);
                assertEquals(data.get(0), arg);
                return null;
            }).when(repository).update(any());
            service.updateTool(data.get(0));
        } catch (RepositoryException | ServiceException e) {
            logger.error(e.getMessage());
        }
    }

    @Test
    void toolsFromCsvShouldValidTest() {
        given(repository.getById(0)).willReturn(data.get(0));
        doAnswer(invocation -> {
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
        when(repository.getAll()).thenReturn(data);
        service.toolsToCsv();
    }

    @AfterAll
    static void tearDown() {
        data.clear();
    }
}