package org.senla.share.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.senla.share.dto.ProfileDto;
import org.senla.share.dto.UserDto;
import org.senla.share.entity.User;
import org.senla.share.mapper.ProfileMapper;
import org.senla.share.mapper.UserMapper;
import org.senla.share.repository.UserRepository;
import org.senla.share.service.RoleService;
import org.senla.share.service.UserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.EntityExistsException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultUserServiceTest {
    private final static Logger logger = LogManager.getLogger(DefaultUserServiceTest.class);
    private static List<User> userList = new ArrayList<>();
    private static List<UserDto> userDtoList = new ArrayList<>();
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private ProfileMapper profileMapper;
    @Mock
    private RoleService roleService;
    @Mock
    private PasswordEncoder encoder;
    @InjectMocks
    private UserService userService = new DefaultUserService(userRepository, userMapper);

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setId(1);
        user.setUsername("user1");
        userList.add(user);
        UserDto userDto = new UserDto();
        userDto.setUsername("user1");
        userDtoList.add(userDto);
    }

    @AfterEach
    void tearDown() {
        userList.clear();
        userDtoList.clear();
    }

    @Test
    void saveUserShouldThrowExceptionTest() {
        UserDto userDto = userDtoList.get(0);
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(userList.get(0)));
        assertThrows(EntityExistsException.class, () -> userService.save(userDto));
    }

    @Test
    void saveUserShouldThrowExceptionWhenAddNullTest() {
        assertThrows(NullPointerException.class, () -> userService.save(null));
    }

    @Test
    void deleteUserShouldVerifyTest() {
        userService.delete(userList.get(0).getId());
        verify(userRepository, times(1)).delete(anyInt());
    }

    @Test
    void updateUserShouldValidTest() {
        User user = userList.get(0);
        UserDto userDto = userDtoList.get(0);
        when(userMapper.entityToDto(any(User.class))).thenReturn(userDto);
        userService.update(userMapper.entityToDto(user));
    }

    @Test
    void getByIdShouldBeNotNullTest() {
        final int id = 0;
        given(userRepository.getById(id)).willReturn(Optional.of(userList.get(id)));
        assertNotNull(userService.getById(id));
    }

    @Test
    void getAllShouldReturnListTest() {
        when(userRepository.getAll()).thenReturn(userList);
        List<UserDto> expected = userService.getAll();
        assertEquals(userList.size(), expected.size());
    }

    @Test
    void getAllShouldThrowExceptionWhenEmptyTest() {
        when(userRepository.getAll()).thenReturn(null);
        assertThrows(NullPointerException.class, () -> userService.getAll());
    }

    @Test
    void updateProfileShouldThrowExceptionWhenDoesNotExistsTest() {
        ProfileDto profileDto = new ProfileDto();
        profileDto.setUsername("user1");
        assertThrows(UsernameNotFoundException.class, () ->
                userService.updateProfile(profileDto, profileDto.getUsername()));
    }
}