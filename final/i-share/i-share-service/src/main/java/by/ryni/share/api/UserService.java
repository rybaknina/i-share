package by.ryni.share.api;

import by.ryni.share.dto.ProfileDto;
import by.ryni.share.dto.UserDto;
import by.ryni.share.entity.User;
import by.ryni.share.exception.ServiceException;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService, GenericService<UserDto> {
    Optional<User> findByUsername(String username);
    void updateProfile(ProfileDto profileDto, String username) throws ServiceException;
    Optional<ProfileDto> getProfile(String username);
}
