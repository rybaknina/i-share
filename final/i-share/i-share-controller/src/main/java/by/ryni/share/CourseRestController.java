package by.ryni.share;

import by.ryni.share.controller.CourseController;
import by.ryni.share.dto.course.CourseDto;
import by.ryni.share.service.CourseService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/course")
public class CourseRestController extends AbstractRestController<CourseDto, CourseService> implements CourseController {
    //TODO: add all logic
}
