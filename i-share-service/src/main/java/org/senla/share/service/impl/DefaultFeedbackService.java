package org.senla.share.service.impl;

import org.senla.share.dto.CourseDto;
import org.senla.share.dto.FeedbackDto;
import org.senla.share.dto.LessonDto;
import org.senla.share.entity.Feedback;
import org.senla.share.mapper.FeedbackMapper;
import org.senla.share.repository.FeedbackRepository;
import org.senla.share.service.CourseService;
import org.senla.share.service.FeedbackService;
import org.senla.share.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service("feedbackService")
public class DefaultFeedbackService extends AbstractService<FeedbackDto, Feedback, FeedbackRepository> implements FeedbackService {
    private CourseService courseService;
    private LessonService lessonService;

    @Autowired
    public DefaultFeedbackService(@Qualifier("feedbackRepository") FeedbackRepository feedbackRepository, FeedbackMapper feedbackMapper) {
        super(feedbackRepository, feedbackMapper);
    }

    @Autowired
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    @Autowired
    public void setLessonService(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @Transactional
    @Override
    public Optional<FeedbackDto> save(FeedbackDto dto) {
        Optional<CourseDto> course = Optional.empty();
        if (dto.getCourse() != null) {
            course = courseService.getById(dto.getCourse().getId());
        }
        Optional<LessonDto> lesson = Optional.empty();
        if (dto.getLesson() != null) {
            lesson = lessonService.getById(dto.getLesson().getId());
        }
        if (course.isPresent() && lesson.isPresent()) {
            throw new IllegalArgumentException("Lesson or course should be entered");
        }
        if (!course.isPresent() && !lesson.isPresent()) {
            throw new IllegalArgumentException("Just one of the entities (lesson or course) should be entered");
        }
        return super.save(dto);
    }
}
