package by.ryni.share;

import by.ryni.share.dto.course.CourseDto;
import by.ryni.share.dto.course.CourseShortDto;
import by.ryni.share.entity.Course;
import by.ryni.share.mapper.CourseMapper;
import by.ryni.share.repository.CourseRepository;
import by.ryni.share.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("courseService")
public class DefaultCourseService extends AbstractService<CourseDto, CourseShortDto, Course, CourseRepository> implements CourseService {
//    public DefaultCourseService() {
//    }
//
    @Autowired
    public DefaultCourseService(@Qualifier("courseRepository") CourseRepository repository, CourseMapper mapper) {
        super(repository, mapper);
    }
}
