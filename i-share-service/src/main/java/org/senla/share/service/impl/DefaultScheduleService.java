package org.senla.share.service.impl;

import org.senla.share.dto.ScheduleDto;
import org.senla.share.entity.Schedule;
import org.senla.share.mapper.ScheduleMapper;
import org.senla.share.repository.ScheduleRepository;
import org.senla.share.service.LessonService;
import org.senla.share.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service("scheduleService")
public class DefaultScheduleService extends AbstractService<ScheduleDto, Schedule, ScheduleRepository> implements ScheduleService {
    private LessonService lessonService;
    private ScheduleRepository scheduleRepository;
    private ScheduleMapper scheduleMapper;

    @Autowired
    public DefaultScheduleService(@Qualifier("scheduleRepository") ScheduleRepository scheduleRepository, ScheduleMapper scheduleMapper) {
        super(scheduleRepository, scheduleMapper);
    }

    @Autowired
    public void setLessonService(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @Autowired
    public void setScheduleRepository(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Autowired
    public void setScheduleMapper(ScheduleMapper scheduleMapper) {
        this.scheduleMapper = scheduleMapper;
    }

    @Transactional
    @Override
    public Optional<ScheduleDto> save(ScheduleDto dto) {
        if (lessonService.getById(dto.getLesson().getId()).isPresent()) {
            return super.save(dto);
        } else {
            throw new EntityNotFoundException("Lesson for schedule does not found");
        }
    }

    @Transactional(readOnly = true)
    public Set<ScheduleDto> scheduleByLesson(int id) {
        return scheduleRepository.scheduleByLesson(id)
                .stream().map(e -> scheduleMapper.entityToDto(e)).collect(Collectors.toSet());
    }
}
