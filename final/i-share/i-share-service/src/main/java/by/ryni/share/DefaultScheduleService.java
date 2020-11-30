package by.ryni.share;

import by.ryni.share.api.LessonRepository;
import by.ryni.share.dto.ScheduleDto;
import by.ryni.share.entity.Schedule;
import by.ryni.share.exception.ServiceException;
import by.ryni.share.mapper.ScheduleMapper;
import by.ryni.share.api.ScheduleRepository;
import by.ryni.share.api.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("scheduleService")
public class DefaultScheduleService extends AbstractService<ScheduleDto, Schedule, ScheduleRepository> implements ScheduleService {
    private LessonRepository lessonRepository;

    @Autowired
    public DefaultScheduleService(@Qualifier("scheduleRepository") ScheduleRepository repository, ScheduleMapper mapper) {
        super(repository, mapper);
    }

    @Autowired
    public void setLessonRepository(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    @Transactional
    @Override
    public void save(ScheduleDto dto) throws ServiceException {
        if (!lessonRepository.getById(dto.getLessonId()).isPresent()) {
            throw new ServiceException("Lesson does not exists");
        }
        super.save(dto);
    }
}
