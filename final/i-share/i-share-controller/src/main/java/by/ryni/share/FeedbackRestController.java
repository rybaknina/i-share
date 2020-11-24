package by.ryni.share;

import by.ryni.share.dto.FeedbackDto;
import by.ryni.share.ecxeption.ServiceException;
import by.ryni.share.handler.ResponseEntityError;
import by.ryni.share.service.FeedbackService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedbacks")
public class FeedbackRestController {
    private Logger logger = LogManager.getLogger(FeedbackRestController.class);
    private FeedbackService feedbackService;

    @Autowired
    public void setFeedbackService(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody FeedbackDto dto) {
        try {
            feedbackService.save(dto);
            return ResponseEntity.ok(dto);
        } catch (ServiceException e) {
            return ResponseEntityError
                    .objectResponseEntity(logger, "Service exception " + e.getMessage(), e.getLocalizedMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable int id) {
        try {
            feedbackService.delete(id);
            return ResponseEntity.ok(id);
        } catch (ServiceException e) {
            return ResponseEntityError
                    .objectResponseEntity(logger, "Service exception " + e.getMessage(), e.getLocalizedMessage());
        }
    }

    @PatchMapping
    public ResponseEntity<Object> update(@RequestBody FeedbackDto dto) {
        try {
            feedbackService.update(dto);
            return ResponseEntity.ok(dto);
        } catch (ServiceException e) {
            return ResponseEntityError
                    .objectResponseEntity(logger, "Service exception " + e.getMessage(), e.getLocalizedMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable int id) {
        return ResponseEntity.ok(feedbackService.getById(id).get());
    }

    //TODO: add all logic

    @GetMapping
    public @ResponseBody
    List<FeedbackDto> getAll() {
        return feedbackService.getAll();
    }
}
