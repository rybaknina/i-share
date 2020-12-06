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
import org.senla.share.dto.LessonDto;
import org.senla.share.entity.Course;
import org.senla.share.entity.Lesson;
import org.senla.share.mapper.LessonMapper;
import org.senla.share.repository.LessonRepository;
import org.senla.share.service.CourseService;
import org.senla.share.service.LessonService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultLessonServiceTest {
    private final static Logger logger = LogManager.getLogger(DefaultLessonServiceTest.class);
    private static List<Lesson> lessonList = new ArrayList<>();
    private static List<LessonDto> lessonDtoList = new ArrayList<>();
    @Mock
    private LessonRepository lessonRepository;
    @Mock
    private LessonMapper lessonMapper;
    @Mock
    private CourseService courseService;
    @InjectMocks
    private LessonService lessonService = new DefaultLessonService(lessonRepository, lessonMapper);

    @BeforeEach
    void setUp() {
        Lesson lesson = new Lesson();
        lesson.setId(1);
        Course course = new Course();
        course.setId(1);
        lesson.setCourse(course);
        lessonList.add(lesson);
        LessonDto lessonDto = new LessonDto();
        lessonDto.setId(1);
        CourseDto courseDto = new CourseDto();
        courseDto.setId(1);
        lessonDto.setCourse(courseDto);
        lessonDtoList.add(lessonDto);
    }

    @AfterEach
    void tearDown() {
        lessonList.clear();
        lessonDtoList.clear();
    }

    @Test
    void saveLessonShouldValidTest() {
        LessonDto lessonDto = lessonDtoList.get(0);
        when(courseService.getById(anyInt())).thenReturn(Optional.of(lessonDto.getCourse()));
        when(lessonMapper.dtoToEntity(any(LessonDto.class))).thenReturn(lessonList.get(0));
        when(lessonMapper.entityToDto(any(Lesson.class))).thenReturn(lessonDto);
        when(lessonRepository.save(lessonMapper.dtoToEntity(lessonDto))).thenReturn(Optional.of(lessonList.get(0)));
        lessonService.save(lessonDto);
    }

    @Test
    void saveLessonShouldThrowExceptionWhenAddNullTest() {
        assertThrows(NullPointerException.class, () -> lessonService.save(null));
    }

    @Test
    void deleteLessonShouldVerifyTest() {
        lessonService.delete(lessonList.get(0).getId());
        verify(lessonRepository, times(1)).delete(anyInt());
    }

    @Test
    void updateLessonShouldValidTest() {
        Lesson lesson = lessonList.get(0);
        LessonDto lessonDto = lessonDtoList.get(0);
        when(lessonMapper.entityToDto(any(Lesson.class))).thenReturn(lessonDto);
        lessonService.update(lessonMapper.entityToDto(lesson));
    }

    @Test
    void getByIdShouldBeNotNullTest() {
        final int id = 0;
        given(lessonRepository.getById(id)).willReturn(Optional.of(lessonList.get(id)));
        assertNotNull(lessonService.getById(id));
    }

    @Test
    void getAllShouldReturnListTest() {
        when(lessonRepository.getAll()).thenReturn(lessonList);
        List<LessonDto> expected = lessonService.getAll();
        assertEquals(lessonList.size(), expected.size());
    }

    @Test
    void getAllShouldThrowExceptionWhenEmptyTest() {
        when(lessonRepository.getAll()).thenReturn(null);
        assertThrows(NullPointerException.class, () -> lessonService.getAll());
    }

    @Test
    void lessonsByCourseShouldValidTest() {
        when(lessonRepository.lessonsByCourse(anyInt())).thenReturn(lessonList.get(0).getCourse().getLessons());
        lessonService.lessonsByCourse(1);
    }
}