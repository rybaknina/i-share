package eu.senla.course.controller;

import eu.senla.course.api.service.IUserService;
import eu.senla.course.dto.user.UserDto;
import eu.senla.course.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private IUserService userService;

    @Autowired
    @Qualifier("userService")
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping("/users")
    public List<UserDto> getUsers() {
        return userService.getUsers();
    }

    @PostAuthorize("hasRole('MANAGER')")
    @GetMapping("/users/{id}")
    public UserDto userById(@PathVariable int id) {
        return userService.userById(id);
    }

    @PreAuthorize("hasAuthority('delete')")
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }

    @PreAuthorize("hasAnyAuthority('delete', 'write', 'create')")
    @PatchMapping("/users")
    public void updateUser(@RequestBody UserDto userDto) throws ServiceException {
        userService.updateUser(userDto);
    }
}
