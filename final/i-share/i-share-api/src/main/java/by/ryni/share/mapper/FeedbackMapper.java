package by.ryni.share.mapper;

import by.ryni.share.dto.feedback.FeedbackDto;
import by.ryni.share.dto.feedback.FeedbackShortDto;
import by.ryni.share.entity.Feedback;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel  = "spring")
@Component
public interface FeedbackMapper extends GenericMapper<FeedbackDto, FeedbackShortDto, Feedback> {
    FeedbackMapper instance = Mappers.getMapper(FeedbackMapper.class);
}
