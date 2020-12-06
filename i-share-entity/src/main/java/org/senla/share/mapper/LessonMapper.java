package org.senla.share.mapper;

import org.senla.share.dto.LessonDto;
import org.senla.share.entity.Lesson;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Component
public interface LessonMapper extends GenericMapper<LessonDto, Lesson> {
    LessonMapper instance = Mappers.getMapper(LessonMapper.class);
}
