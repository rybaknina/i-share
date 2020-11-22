package by.ryni.share;

import by.ryni.share.controller.DonateController;
import by.ryni.share.dto.donate.DonateDto;
import by.ryni.share.service.DonateService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/donate")
public class DonateRestController extends AbstractRestController<DonateDto, DonateService> implements DonateController {
    //TODO: add all logic
}
