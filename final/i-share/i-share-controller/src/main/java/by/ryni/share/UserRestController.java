package by.ryni.share;

import by.ryni.share.controller.UserController;
import by.ryni.share.dto.user.UserDto;
import by.ryni.share.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserRestController extends AbstractRestController<UserDto, UserService> implements UserController {
    //TODO: add all logic
}
