package org.senla.share.service;

import org.senla.share.dto.ProfileDto;
import org.senla.share.dto.UserDto;
import org.senla.share.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService, GenericService<UserDto> {
    Optional<User> findByUsername(String username);

    Optional<ProfileDto> updateProfile(ProfileDto profileDto, String username);

    Optional<ProfileDto> getProfile(String username);
}
