package by.ryni.share;

import by.ryni.share.dto.ScheduleDto;
import by.ryni.share.entity.Schedule;
import by.ryni.share.mapper.ScheduleMapper;
import by.ryni.share.repository.ScheduleRepository;
import by.ryni.share.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("scheduleService")
public class DefaultScheduleService extends AbstractService<ScheduleDto, Schedule, ScheduleRepository> implements ScheduleService {
    @Autowired
    public DefaultScheduleService(@Qualifier("scheduleRepository") ScheduleRepository repository, ScheduleMapper mapper) {
        super(repository, mapper);
    }
}
