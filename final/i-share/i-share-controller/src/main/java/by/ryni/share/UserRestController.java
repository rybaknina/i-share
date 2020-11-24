package by.ryni.share;

import by.ryni.share.dto.UserDto;
import by.ryni.share.ecxeption.ServiceException;
import by.ryni.share.handler.ResponseEntityError;
import by.ryni.share.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserRestController {
    private Logger logger = LogManager.getLogger(UserRestController.class);
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody UserDto dto) {
        try {
            userService.save(dto);
            return ResponseEntity.ok(dto);
        } catch (ServiceException e) {
            return ResponseEntityError
                    .objectResponseEntity(logger, "Service exception " + e.getMessage(), e.getLocalizedMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable int id) {
        try {
            userService.delete(id);
            return ResponseEntity.ok(id);
        } catch (ServiceException e) {
            return ResponseEntityError
                    .objectResponseEntity(logger, "Service exception " + e.getMessage(), e.getLocalizedMessage());
        }
    }

    @PatchMapping
    public ResponseEntity<Object> update(@RequestBody UserDto dto) {
        try {
            userService.update(dto);
            return ResponseEntity.ok(dto);
        } catch (ServiceException e) {
            return ResponseEntityError
                    .objectResponseEntity(logger, "Service exception " + e.getMessage(), e.getLocalizedMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable int id) {
        return ResponseEntity.ok(userService.getById(id).get());
    }

    //TODO: add all logic

    @GetMapping
    public @ResponseBody List<UserDto> getAll() {
        return userService.getAll();
    }
//TODO: add all logic
}
