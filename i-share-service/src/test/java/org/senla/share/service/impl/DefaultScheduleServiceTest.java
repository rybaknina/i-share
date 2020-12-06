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
import org.senla.share.dto.LessonDto;
import org.senla.share.dto.ScheduleDto;
import org.senla.share.entity.Lesson;
import org.senla.share.entity.Schedule;
import org.senla.share.mapper.ScheduleMapper;
import org.senla.share.repository.ScheduleRepository;
import org.senla.share.service.LessonService;
import org.senla.share.service.ScheduleService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultScheduleServiceTest {
    private final static Logger logger = LogManager.getLogger(DefaultScheduleServiceTest.class);
    private static List<Schedule> scheduleList = new ArrayList<>();
    private static List<ScheduleDto> scheduleDtoList = new ArrayList<>();
    @Mock
    private ScheduleRepository scheduleRepository;
    @Mock
    private ScheduleMapper scheduleMapper;
    @Mock
    private LessonService lessonService;
    @InjectMocks
    private ScheduleService scheduleService = new DefaultScheduleService(scheduleRepository, scheduleMapper);

    @BeforeEach
    void setUp() {
        Schedule schedule = new Schedule();
        schedule.setId(1);
        Lesson lesson = new Lesson();
        lesson.setId(1);
        schedule.setLesson(lesson);
        scheduleList.add(schedule);
        ScheduleDto scheduleDto = new ScheduleDto();
        scheduleDto.setId(1);
        LessonDto lessonDto = new LessonDto();
        lessonDto.setId(1);
        scheduleDto.setLesson(lessonDto);
        scheduleDtoList.add(scheduleDto);
    }

    @AfterEach
    void tearDown() {
        scheduleList.clear();
        scheduleDtoList.clear();
    }

    @Test
    void saveScheduleShouldValidTest() {
        ScheduleDto scheduleDto = scheduleDtoList.get(0);
        when(lessonService.getById(anyInt())).thenReturn(Optional.of(scheduleDto.getLesson()));
        when(scheduleMapper.dtoToEntity(any(ScheduleDto.class))).thenReturn(scheduleList.get(0));
        when(scheduleMapper.entityToDto(any(Schedule.class))).thenReturn(scheduleDto);
        when(scheduleRepository.save(scheduleMapper.dtoToEntity(scheduleDto))).thenReturn(Optional.of(scheduleList.get(0)));
        scheduleService.save(scheduleDto);
    }

    @Test
    void saveScheduleShouldThrowExceptionWhenAddNullTest() {
        assertThrows(NullPointerException.class, () -> scheduleService.save(null));
    }

    @Test
    void deleteScheduleShouldVerifyTest() {
        scheduleService.delete(scheduleList.get(0).getId());
        verify(scheduleRepository, times(1)).delete(anyInt());
    }

    @Test
    void updateScheduleShouldValidTest() {
        Schedule schedule = scheduleList.get(0);
        ScheduleDto scheduleDto = scheduleDtoList.get(0);
        when(scheduleMapper.entityToDto(any(Schedule.class))).thenReturn(scheduleDto);
        scheduleService.update(scheduleMapper.entityToDto(schedule));
    }

    @Test
    void getByIdShouldBeNotNullTest() {
        final int id = 0;
        given(scheduleRepository.getById(id)).willReturn(Optional.of(scheduleList.get(id)));
        assertNotNull(scheduleService.getById(id));
    }

    @Test
    void getAllShouldReturnListTest() {
        when(scheduleRepository.getAll()).thenReturn(scheduleList);
        List<ScheduleDto> expected = scheduleService.getAll();
        assertEquals(scheduleList.size(), expected.size());
    }

    @Test
    void getAllShouldThrowExceptionWhenEmptyTest() {
        when(scheduleRepository.getAll()).thenReturn(null);
        assertThrows(NullPointerException.class, () -> scheduleService.getAll());
    }

    @Test
    void scheduleByLessonShouldValidTest() {
        when(scheduleRepository.scheduleByLesson(anyInt())).thenReturn(scheduleList.get(0).getLesson().getSchedules());
        scheduleService.scheduleByLesson(1);
    }
}