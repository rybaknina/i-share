package by.ryni.share;

import by.ryni.share.dto.LessonDto;
import by.ryni.share.entity.Lesson;
import by.ryni.share.mapper.LessonMapper;
import by.ryni.share.repository.LessonRepository;
import by.ryni.share.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("lessonService")
public class DefaultLessonService extends AbstractService<LessonDto, Lesson, LessonRepository> implements LessonService {
    @Autowired
    public DefaultLessonService(@Qualifier("lessonRepository") LessonRepository repository, LessonMapper mapper) {
        super(repository, mapper);
    }
}
