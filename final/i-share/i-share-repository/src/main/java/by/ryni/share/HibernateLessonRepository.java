package by.ryni.share;

import by.ryni.share.entity.Lesson;
import by.ryni.share.repository.LessonRepository;
import org.springframework.stereotype.Repository;

@Repository("lessonRepository")
public class HibernateLessonRepository extends AbstractHibernateRepository<Lesson> implements LessonRepository {
    public HibernateLessonRepository() {
        super(Lesson.class);
    }
}
