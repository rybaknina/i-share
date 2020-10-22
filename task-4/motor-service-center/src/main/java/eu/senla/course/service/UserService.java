package eu.senla.course.service;

import eu.senla.course.api.repository.IUserRepository;
import eu.senla.course.api.service.IRoleService;
import eu.senla.course.api.service.IUserService;
import eu.senla.course.dto.permission.PermissionDto;
import eu.senla.course.dto.role.RoleDto;
import eu.senla.course.dto.user.UserDto;
import eu.senla.course.entity.Role;
import eu.senla.course.entity.User;
import eu.senla.course.exception.RepositoryException;
import eu.senla.course.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service("userService")
public class UserService implements UserDetailsService, IUserService {
    private final static Logger logger = LogManager.getLogger(UserService.class);
    private IUserRepository userRepository;
    private IRoleService roleService;
    private PasswordEncoder encoder;
    private final static String PREFIX = "ROLE_";

    @Autowired
    @Qualifier("userHibernateRepository")
    public void setUserRepository(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    @Qualifier("roleService")
    public void setRoleService(IRoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setEncoder(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    public User userDtoToEntity(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(encoder.encode(userDto.getPassword()));
        user.setActive(userDto.isActive());
        List<Role> roles = new ArrayList<>();
        if (userDto.getRoleDtoList() != null) {
            for (RoleDto roleDto: userDto.getRoleDtoList()) {
                roles.add(roleService.roleDtoToEntity(roleDto));
            }
        }
        user.setRoles(roles);
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = this.userRepository.findByUsername(username);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        UserDto userDto = new UserDto(user.get());
        return new org.springframework.security.core.userdetails.User(userDto.getUsername(), userDto.getPassword(), getAuthority(userDto));
    }

    private Set<SimpleGrantedAuthority> getAuthority(UserDto userDto) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        if (userDto.getRoleDtoList() != null) {
            for (RoleDto roleDto: userDto.getRoleDtoList()) {
                authorities.add(new SimpleGrantedAuthority(PREFIX + roleDto.getName()));
                if (roleDto.getPermissions() != null) {
                    for (PermissionDto permissionDto: roleDto.getPermissions()) {
                        authorities.add(new SimpleGrantedAuthority(permissionDto.getName()));
                    }
                }
            }
        }
        return authorities;
    }

    @Transactional
    public void save(UserDto userDto) throws ServiceException {
        try {
            if (this.userRepository.findByUsername(userDto.getUsername()).isPresent()) {
                throw new ServiceException("User already exist with username " + userDto.getUsername());
            }
            this.userRepository.add(this.userDtoToEntity(userDto));
        } catch (RepositoryException e) {
            logger.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<UserDto> getUsers() {
        return this.userRepository.getAll().stream().map(UserDto::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserDto userById(int id) {
        return new UserDto(this.userRepository.getById(id));
    }

    @Transactional
    public void deleteUser(int id) {
        userRepository.delete(id);
    }

    @Transactional
    public void updateUser(UserDto userDto) throws ServiceException {
        try {
            userRepository.update(userDtoToEntity(userDto));
        } catch (RepositoryException e) {
            throw new ServiceException("RepositoryException " + e.getMessage());
        }
    }
}
