package by.ryni.share;

import by.ryni.share.controller.ScheduleController;
import by.ryni.share.dto.schedule.ScheduleDto;
import by.ryni.share.service.ScheduleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/schedule")
public class ScheduleRestController extends AbstractRestController<ScheduleDto, ScheduleService> implements ScheduleController {
    //TODO: add all logic
}
