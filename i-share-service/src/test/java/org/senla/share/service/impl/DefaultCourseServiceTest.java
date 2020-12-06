package org.senla.share.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.senla.share.dto.CourseDto;
import org.senla.share.dto.ThemeDto;
import org.senla.share.entity.Course;
import org.senla.share.mapper.CourseMapper;
import org.senla.share.repository.CourseRepository;
import org.senla.share.service.CourseService;
import org.senla.share.service.ThemeService;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultCourseServiceTest {
    private final static Logger logger = LogManager.getLogger(DefaultCourseServiceTest.class);
    private static List<Course> courseList = new ArrayList<>();
    private static List<CourseDto> courseDtoList = new ArrayList<>();
    @Mock
    private CourseRepository courseRepository;
    @Mock
    private CourseMapper courseMapper;
    @Mock
    private ThemeService themeService;
    @InjectMocks
    private CourseService courseService = new DefaultCourseService(courseRepository, courseMapper);

    @BeforeEach
    void setUp() {
        Course course = new Course();
        course.setId(1);
        course.setTitle("Course 1");
        courseList.add(course);
        CourseDto courseDto = new CourseDto();
        courseDto.setId(1);
        courseDto.setTitle("Course 1");
        ThemeDto themeDto = new ThemeDto();
        themeDto.setId(1);
        courseDto.setTheme(themeDto);
        courseDtoList.add(courseDto);
    }

    @AfterEach
    void tearDown() {
        courseList.clear();
        courseDtoList.clear();
    }

    @Test
    void saveCourseShouldThrowExceptionWhenAddNullTest() {
        assertThrows(EntityNotFoundException.class, () -> courseService.save(courseDtoList.get(0)));
    }

    @Test
    void deleteCourseShouldVerifyTest() {
        courseService.delete(courseList.get(0).getId());
        verify(courseRepository, times(1)).delete(anyInt());
    }

    @Test
    void updateCourseShouldValidTest() {
        Course Course = courseList.get(0);
        CourseDto CourseDto = courseDtoList.get(0);
        when(courseMapper.entityToDto(any(Course.class))).thenReturn(CourseDto);
        courseService.update(courseMapper.entityToDto(Course));
    }

    @Test
    void getByIdShouldBeNotNullTest() {
        final int id = 0;
        given(courseRepository.getById(id)).willReturn(Optional.of(courseList.get(id)));
        assertNotNull(courseService.getById(id));
    }

    @Test
    void getAllShouldReturnListTest() {
        when(courseRepository.getAll()).thenReturn(courseList);
        List<CourseDto> expected = courseService.getAll();
        assertEquals(courseList.size(), expected.size());
    }

    @Test
    void getAllShouldThrowExceptionWhenEmptyTest() {
        when(courseRepository.getAll()).thenReturn(null);
        assertThrows(NullPointerException.class, () -> courseService.getAll());
    }
}