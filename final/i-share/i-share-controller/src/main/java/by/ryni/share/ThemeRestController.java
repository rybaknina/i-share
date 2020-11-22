package by.ryni.share;

import by.ryni.share.controller.ThemeController;
import by.ryni.share.dto.theme.ThemeDto;
import by.ryni.share.service.ThemeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/theme")
public class ThemeRestController extends AbstractRestController<ThemeDto, ThemeService> implements ThemeController {
    //TODO: add all logic
}
