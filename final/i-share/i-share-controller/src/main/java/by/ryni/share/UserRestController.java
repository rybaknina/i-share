package by.ryni.share;

import by.ryni.share.dto.ProfileDto;
import by.ryni.share.dto.UserDto;
import by.ryni.share.exception.ServiceException;
import by.ryni.share.handler.ResponseEntityError;
import by.ryni.share.api.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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

    @PreAuthorize("hasRole('ADMIN')")
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

    @PreAuthorize("hasRole('ADMIN')")
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

    @PreAuthorize("hasRole('ADMIN')")
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

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable int id) {
        return ResponseEntity.ok(userService.getById(id).get());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public @ResponseBody List<UserDto> getAll() {
        return userService.getAll();
    }

    @PatchMapping("/profile")
    public ResponseEntity<Object> profile(@RequestBody ProfileDto dto, Principal principal) {
        try {
            userService.updateProfile(dto, principal.getName());
            return ResponseEntity.ok(dto);
        } catch (ServiceException e) {
            return ResponseEntityError
                    .objectResponseEntity(logger, "Service exception " + e.getMessage(), e.getLocalizedMessage());
        }
    }

    @GetMapping("profile")
    public ResponseEntity<Object> profile(Principal principal) {
        return ResponseEntity.ok(userService.getProfile(principal.getName()).get());
    }
}
