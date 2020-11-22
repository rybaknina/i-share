package by.ryni.share.mapper;

import by.ryni.share.dto.lesson.LessonDto;
import by.ryni.share.dto.lesson.LessonShortDto;
import by.ryni.share.entity.Lesson;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel  = "spring")
@Component
public interface LessonMapper extends GenericMapper<LessonDto, LessonShortDto, Lesson> {
    LessonMapper instance = Mappers.getMapper(LessonMapper.class);
//    LessonDto lessonToLessonDto(Lesson lesson);
//    Lesson lessonDtoToLesson(LessonDto lessonDto);
//    Lesson lessonShortDtoToLesson(LessonShortDto lessonShortDto);
}
