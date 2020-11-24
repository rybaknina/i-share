package by.ryni.share.mapper;

import by.ryni.share.dto.CourseDto;
import by.ryni.share.entity.Course;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel  = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Component
public interface CourseMapper extends GenericMapper<CourseDto, Course> {
    CourseMapper instance = Mappers.getMapper(CourseMapper.class);
}
