package by.ryni.share.mapper;

import by.ryni.share.dto.LessonDto;
import by.ryni.share.entity.Lesson;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel  = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Component
public interface LessonMapper extends GenericMapper<LessonDto, Lesson> {
    LessonMapper instance = Mappers.getMapper(LessonMapper.class);
}
