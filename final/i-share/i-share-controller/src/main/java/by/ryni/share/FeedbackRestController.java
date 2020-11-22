package by.ryni.share;

import by.ryni.share.controller.FeedbackController;
import by.ryni.share.dto.feedback.FeedbackDto;
import by.ryni.share.service.FeedbackService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feedback")
public class FeedbackRestController extends AbstractRestController<FeedbackDto, FeedbackService> implements FeedbackController {
    //TODO: add all logic
}
