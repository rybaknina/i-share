package by.ryni.share.helper;

import by.ryni.share.dto.UserDto;
import by.ryni.share.entity.User;
import by.ryni.share.mapper.UserMapper;
import by.ryni.share.api.UserService;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.Optional;

@Component
public class UserHelper {
    private UserService userService;
    private UserMapper userMapper;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public UserDto setCurrentUser(Logger logger, Principal principal) {
        if (principal == null) {
            logger.error("Authentication is not valid");
            throw new AuthenticationCredentialsNotFoundException("Authentication is not valid");
        }

        Optional<User> user = userService.findByUsername(principal.getName());
        if (!user.isPresent()) {
            logger.error("Owner is not found");
            throw new UsernameNotFoundException("Owner is not found");
        }
        return userMapper.entityToDto(user.get());
    }
}
