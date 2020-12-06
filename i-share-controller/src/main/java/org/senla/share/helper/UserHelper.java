package org.senla.share.helper;

import org.apache.logging.log4j.Logger;
import org.senla.share.dto.UserDto;
import org.senla.share.entity.AbstractEntity;
import org.senla.share.entity.User;
import org.senla.share.mapper.UserMapper;
import org.senla.share.service.RoleService;
import org.senla.share.service.UserService;
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
    private RoleService roleService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
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

    public int userIdByPrincipal(Logger logger, Principal principal) {
        if (principal == null) {
            logger.error("Authentication is not valid");
            throw new AuthenticationCredentialsNotFoundException("Authentication is not valid");
        }

        Optional<User> user = userService.findByUsername(principal.getName());
        return user.map(AbstractEntity::getId).orElse(0);
    }
}
