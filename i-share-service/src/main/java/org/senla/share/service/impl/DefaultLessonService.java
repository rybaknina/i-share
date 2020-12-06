package org.senla.share.service.impl;

import org.senla.share.criteria.SearchCriteria;
import org.senla.share.dto.LessonDto;
import org.senla.share.entity.Lesson;
import org.senla.share.mapper.LessonMapper;
import org.senla.share.repository.LessonRepository;
import org.senla.share.service.CourseService;
import org.senla.share.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service("lessonService")
public class DefaultLessonService extends AbstractService<LessonDto, Lesson, LessonRepository> implements LessonService {
    private LessonRepository lessonRepository;
    private LessonMapper lessonMapper;
    private CourseService courseService;

    @Autowired
    public DefaultLessonService(@Qualifier("lessonRepository") LessonRepository lessonRepository, LessonMapper lessonMapper) {
        super(lessonRepository, lessonMapper);
    }

    @Autowired
    public void setLessonRepository(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    @Autowired
    public void setLessonMapper(LessonMapper lessonMapper) {
        this.lessonMapper = lessonMapper;
    }

    @Autowired
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    @Transactional
    @Override
    public Optional<LessonDto> save(LessonDto dto) {
        if (courseService.getById(dto.getCourse().getId()).isPresent()) {
            return super.save(dto);
        } else {
            throw new EntityNotFoundException("Course for lesson does not found");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<LessonDto> search(List<SearchCriteria> params) {
        List<LessonDto> lessonDtoList = lessonRepository.search(params)
                .stream()
                .map(e -> lessonMapper.entityToDto(e))
                .collect(Collectors.toList());
        return lessonDtoList;
    }

    @Transactional(readOnly = true)
    @Override
    public Set<LessonDto> lessonsByCourse(int id) {
        return lessonRepository.lessonsByCourse(id)
                .stream().map(e -> lessonMapper.entityToDto(e)).collect(Collectors.toSet());
    }
}
