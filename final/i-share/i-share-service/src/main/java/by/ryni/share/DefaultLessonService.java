package by.ryni.share;

import by.ryni.share.api.CourseRepository;
import by.ryni.share.criteria.SearchCriteria;
import by.ryni.share.dto.LessonDto;
import by.ryni.share.entity.Lesson;
import by.ryni.share.exception.ServiceException;
import by.ryni.share.mapper.LessonMapper;
import by.ryni.share.api.LessonRepository;
import by.ryni.share.api.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service("lessonService")
public class DefaultLessonService extends AbstractService<LessonDto, Lesson, LessonRepository> implements LessonService {
    private LessonRepository lessonRepository;
    private LessonMapper lessonMapper;
    private CourseRepository courseRepository;

    @Autowired
    public DefaultLessonService(@Qualifier("lessonRepository") LessonRepository repository, LessonMapper mapper) {
        super(repository, mapper);
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
    public void setCourseRepository(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Transactional
    @Override
    public void save(LessonDto dto) throws ServiceException {
        if (!courseRepository.getById(dto.getCourseId()).isPresent()) {
            throw new ServiceException("Course does not exists");
        }
        super.save(dto);
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
}
