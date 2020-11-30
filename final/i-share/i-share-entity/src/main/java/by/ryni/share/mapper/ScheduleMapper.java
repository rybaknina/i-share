package by.ryni.share.mapper;

import by.ryni.share.dto.ScheduleDto;
import by.ryni.share.entity.Schedule;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel  = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Component
public interface ScheduleMapper extends GenericMapper<ScheduleDto, Schedule> {
    ScheduleMapper instance = Mappers.getMapper(ScheduleMapper.class);
}
