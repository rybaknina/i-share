package by.ryni.share;

import by.ryni.share.dto.feedback.FeedbackDto;
import by.ryni.share.dto.feedback.FeedbackShortDto;
import by.ryni.share.entity.Feedback;
import by.ryni.share.mapper.FeedbackMapper;
import by.ryni.share.repository.FeedbackRepository;
import by.ryni.share.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("feedbackService")
public class DefaultFeedbackService extends AbstractService<FeedbackDto, FeedbackShortDto, Feedback, FeedbackRepository> implements FeedbackService {

    @Autowired
    public DefaultFeedbackService(@Qualifier("feedbackRepository") FeedbackRepository repository, FeedbackMapper mapper) {
        super(repository, mapper);
    }
}
