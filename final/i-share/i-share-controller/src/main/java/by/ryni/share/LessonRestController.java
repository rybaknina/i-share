package by.ryni.share;

import by.ryni.share.controller.LessonController;
import by.ryni.share.dto.lesson.LessonDto;
import by.ryni.share.service.LessonService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lesson")
public class LessonRestController extends AbstractRestController<LessonDto, LessonService> implements LessonController {
    //TODO: add all logic
}
