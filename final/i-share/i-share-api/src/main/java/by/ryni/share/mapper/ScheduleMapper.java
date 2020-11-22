package by.ryni.share.mapper;

import by.ryni.share.dto.schedule.ScheduleDto;
import by.ryni.share.dto.schedule.ScheduleShortDto;
import by.ryni.share.entity.Schedule;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel  = "spring")
@Component
public interface ScheduleMapper extends GenericMapper<ScheduleDto, ScheduleShortDto, Schedule> {
    ScheduleMapper instance = Mappers.getMapper(ScheduleMapper.class);
}
