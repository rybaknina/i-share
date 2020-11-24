package by.ryni.share.service;

import by.ryni.share.dto.UserDto;
import by.ryni.share.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService, GenericService<UserDto> {
    Optional<User> findByUsername(String username);
}
