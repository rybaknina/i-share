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
import org.senla.share.dto.FeedbackDto;
import org.senla.share.entity.Course;
import org.senla.share.entity.Feedback;
import org.senla.share.mapper.FeedbackMapper;
import org.senla.share.repository.FeedbackRepository;
import org.senla.share.service.CourseService;
import org.senla.share.service.FeedbackService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultFeedbackServiceTest {
    private final static Logger logger = LogManager.getLogger(DefaultFeedbackServiceTest.class);
    private static List<Feedback> feedbackList = new ArrayList<>();
    private static List<FeedbackDto> feedbackDtoList = new ArrayList<>();
    @Mock
    private FeedbackRepository feedbackRepository;
    @Mock
    private FeedbackMapper feedbackMapper;
    @Mock
    private CourseService courseService;
    @InjectMocks
    private FeedbackService feedbackService = new DefaultFeedbackService(feedbackRepository, feedbackMapper);

    @BeforeEach
    void setUp() {
        Feedback feedback = new Feedback();
        feedback.setId(1);
        Course course = new Course();
        course.setId(1);
        feedback.setCourse(course);
        feedbackList.add(feedback);
        FeedbackDto feedbackDto = new FeedbackDto();
        feedbackDto.setId(1);
        CourseDto courseDto = new CourseDto();
        courseDto.setId(1);
        feedbackDto.setCourse(courseDto);
        feedbackDtoList.add(feedbackDto);
    }

    @AfterEach
    void tearDown() {
        feedbackList.clear();
        feedbackDtoList.clear();
    }

    @Test
    void saveFeedbackShouldValidTest() {
        FeedbackDto feedbackDto = feedbackDtoList.get(0);
        when(courseService.getById(anyInt())).thenReturn(Optional.of(feedbackDto.getCourse()));
        when(feedbackMapper.dtoToEntity(any(FeedbackDto.class))).thenReturn(feedbackList.get(0));
        when(feedbackMapper.entityToDto(any(Feedback.class))).thenReturn(feedbackDto);
        when(feedbackRepository.save(feedbackMapper.dtoToEntity(feedbackDto))).thenReturn(Optional.of(feedbackList.get(0)));
        feedbackService.save(feedbackDto);
    }

    @Test
    void saveFeedbackShouldThrowExceptionWhenAddNullTest() {
        assertThrows(NullPointerException.class, () -> feedbackService.save(null));
    }

    @Test
    void deleteFeedbackShouldVerifyTest() {
        feedbackService.delete(feedbackList.get(0).getId());
        verify(feedbackRepository, times(1)).delete(anyInt());
    }

    @Test
    void updateFeedbackShouldValidTest() {
        Feedback feedback = feedbackList.get(0);
        FeedbackDto feedbackDto = feedbackDtoList.get(0);
        when(feedbackMapper.entityToDto(any(Feedback.class))).thenReturn(feedbackDto);
        feedbackService.update(feedbackMapper.entityToDto(feedback));
    }

    @Test
    void getByIdShouldBeNotNullTest() {
        final int id = 0;
        given(feedbackRepository.getById(id)).willReturn(Optional.of(feedbackList.get(id)));
        assertNotNull(feedbackService.getById(id));
    }

    @Test
    void getAllShouldReturnListTest() {
        when(feedbackRepository.getAll()).thenReturn(feedbackList);
        List<FeedbackDto> expected = feedbackService.getAll();
        assertEquals(feedbackList.size(), expected.size());
    }

    @Test
    void getAllShouldThrowExceptionWhenEmptyTest() {
        when(feedbackRepository.getAll()).thenReturn(null);
        assertThrows(NullPointerException.class, () -> feedbackService.getAll());
    }
}