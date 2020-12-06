package org.senla.share.service;

import org.senla.share.dto.ScheduleDto;

import java.util.Set;

public interface ScheduleService extends GenericService<ScheduleDto> {
    Set<ScheduleDto> scheduleByLesson(int id);
}
