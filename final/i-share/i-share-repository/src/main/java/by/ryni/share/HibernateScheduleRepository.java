package by.ryni.share;

import by.ryni.share.entity.Schedule;
import by.ryni.share.repository.ScheduleRepository;
import org.springframework.stereotype.Repository;

@Repository("scheduleRepository")
public class HibernateScheduleRepository extends AbstractHibernateRepository<Schedule> implements ScheduleRepository {
    public HibernateScheduleRepository() {
        super(Schedule.class);
    }
}
