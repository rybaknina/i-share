package by.ryni.share.mapper;

import by.ryni.share.dto.FeedbackDto;
import by.ryni.share.entity.Feedback;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel  = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Component
public interface FeedbackMapper extends GenericMapper<FeedbackDto, Feedback> {
    FeedbackMapper instance = Mappers.getMapper(FeedbackMapper.class);
}
