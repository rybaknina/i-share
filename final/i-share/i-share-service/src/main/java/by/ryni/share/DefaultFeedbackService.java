package by.ryni.share;

import by.ryni.share.api.CourseRepository;
import by.ryni.share.api.LessonRepository;
import by.ryni.share.dto.FeedbackDto;
import by.ryni.share.entity.Course;
import by.ryni.share.entity.Feedback;
import by.ryni.share.entity.Lesson;
import by.ryni.share.exception.ServiceException;
import by.ryni.share.mapper.FeedbackMapper;
import by.ryni.share.api.FeedbackRepository;
import by.ryni.share.api.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service("feedbackService")
public class DefaultFeedbackService extends AbstractService<FeedbackDto, Feedback, FeedbackRepository> implements FeedbackService {
    private CourseRepository courseRepository;
    private LessonRepository lessonRepository;
    @Autowired
    public DefaultFeedbackService(@Qualifier("feedbackRepository") FeedbackRepository repository, FeedbackMapper mapper) {
        super(repository, mapper);
    }

    @Autowired
    public void setCourseRepository(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Autowired
    public void setLessonRepository(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    @Transactional
    @Override
    public void save(FeedbackDto dto) throws ServiceException {
        Optional<Course> course = courseRepository.getById(dto.getCourseId());
        Optional<Lesson> lesson = lessonRepository.getById(dto.getLessonId());
        if (course.isPresent() && lesson.isPresent()) {
            throw new ServiceException("You can leave feedback only for one of them: lesson or course");
        }
        if (!course.isPresent() && !lesson.isPresent()) {
            throw new ServiceException("You should choose one of them: lesson or course");
        }
        super.save(dto);
    }
}
