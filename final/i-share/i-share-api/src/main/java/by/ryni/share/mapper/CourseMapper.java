package by.ryni.share.mapper;

import by.ryni.share.dto.course.CourseDto;
import by.ryni.share.dto.course.CourseShortDto;
import by.ryni.share.entity.Course;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel  = "spring")
@Component
public interface CourseMapper extends GenericMapper<CourseDto, CourseShortDto, Course> {
    CourseMapper instance = Mappers.getMapper(CourseMapper.class);
}
