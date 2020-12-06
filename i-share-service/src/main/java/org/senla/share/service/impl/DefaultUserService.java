package org.senla.share.service.impl;

import org.senla.share.dto.ProfileDto;
import org.senla.share.dto.RoleDto;
import org.senla.share.dto.UserDto;
import org.senla.share.entity.User;
import org.senla.share.enums.UserRole;
import org.senla.share.mapper.ProfileMapper;
import org.senla.share.mapper.RoleMapper;
import org.senla.share.mapper.UserMapper;
import org.senla.share.repository.UserRepository;
import org.senla.share.service.RoleService;
import org.senla.share.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service("userService")
public class DefaultUserService extends AbstractService<UserDto, User, UserRepository> implements UserService {
    private final static String PREFIX = "";
    private UserRepository repository;
    private UserMapper userMapper;
    private ProfileMapper profileMapper;
    private RoleService roleService;
    private RoleMapper roleMapper;
    private PasswordEncoder encoder;

    @Autowired
    public DefaultUserService(@Qualifier("userRepository") UserRepository repository, UserMapper userMapper) {
        super(repository, userMapper);
    }

    @Autowired
    @Qualifier("userRepository")
    public void setRepository(UserRepository repository) {
        this.repository = repository;
    }

    @Autowired
    @Qualifier("roleService")
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setProfileMapper(ProfileMapper profileMapper) {
        this.profileMapper = profileMapper;
    }

    @Autowired
    public void setRoleMapper(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Autowired
    public void setEncoder(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Transactional
    @Override
    public Optional<UserDto> save(UserDto userDto) {
        Optional<User> user = this.repository.findByUsername(userDto.getUsername());
        if (!user.isPresent()) {
            userDto.setPassword(encoder.encode(userDto.getPassword()));
            RoleDto userRole = roleService.findByName(UserRole.USER.name()).get();
            userDto.setRoles(new HashSet<>(Collections.singletonList(userRole)));
            return super.save(userDto);
        } else {
            throw new EntityExistsException("User already exists");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = this.repository.findByUsername(username);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        UserDto userDto = userMapper.entityToDto(user.get());
        return new org.springframework.security.core.userdetails.User(userDto.getUsername(), userDto.getPassword(), getAuthority(userDto));
    }

    private Set<SimpleGrantedAuthority> getAuthority(UserDto userDto) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        if (userDto.getRoles() != null) {
            for (RoleDto role : userDto.getRoles()) {
                authorities.add(new SimpleGrantedAuthority(PREFIX + role.getName()));
            }
        }
        return authorities;
    }

    @Transactional
    @Override
    public Optional<ProfileDto> updateProfile(ProfileDto profileDto, String username) {
        profileDto.setUsername(username);
        Optional<User> findUser = this.repository.findByUsername(username);
        if (!findUser.isPresent()) {
            throw new UsernameNotFoundException("User does not found.");
        }
        if (profileDto.getPassword() != null) {
            profileDto.setPassword(encoder.encode(profileDto.getPassword()));
        }
        User user = profileMapper.dtoToEntity(profileDto, findUser.get());
        return Optional.ofNullable(profileMapper.entityToDto(repository.update(user).get()));
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<ProfileDto> getProfile(String username) {
        Optional<User> user = repository.findByUsername(username);
        if (!user.isPresent()) {
            return Optional.empty();
        }
        ProfileDto profileDto = profileMapper.entityToDto(user.get());
        return Optional.ofNullable(profileDto);
    }
}
