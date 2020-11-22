package by.ryni.share;

import by.ryni.share.entity.Course;
import by.ryni.share.repository.CourseRepository;
import org.springframework.stereotype.Repository;

@Repository("courseRepository")
public class HibernateCourseRepository  extends AbstractHibernateRepository<Course> implements CourseRepository {
    public HibernateCourseRepository() {
        super(Course.class);
    }
}
