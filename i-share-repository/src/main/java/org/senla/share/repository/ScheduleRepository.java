package org.senla.share.repository;

import org.senla.share.entity.Schedule;

import java.util.Set;

public interface ScheduleRepository extends GenericRepository<Schedule> {
    Set<Schedule> scheduleByLesson(int id);
}
