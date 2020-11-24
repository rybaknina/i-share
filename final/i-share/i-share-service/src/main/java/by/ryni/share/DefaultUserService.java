package by.ryni.share;

import by.ryni.share.dto.RoleDto;
import by.ryni.share.dto.UserDto;
import by.ryni.share.ecxeption.ServiceException;
import by.ryni.share.entity.User;
import by.ryni.share.enums.UserRole;
import by.ryni.share.mapper.RoleMapper;
import by.ryni.share.mapper.UserMapper;
import by.ryni.share.repository.RoleRepository;
import by.ryni.share.repository.UserRepository;
import by.ryni.share.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service("userService")
public class DefaultUserService extends AbstractService<UserDto, User, UserRepository> implements UserService {
    private final static String PREFIX = "ROLE_";
    private UserRepository repository;
    private RoleRepository roleRepository;
    private UserMapper userMapper;
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
    @Qualifier("roleRepository")
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
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
    public void save(UserDto userDto) throws ServiceException {
        userDto.setPassword(encoder.encode(userDto.getPassword()));
        RoleDto userRole = roleMapper.entityToDto(roleRepository.findByName(UserRole.USER.name()).get());
        userDto.setRoles(new HashSet<>(Collections.singletonList(userRole)));
        super.save(userDto);
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
//        userDto.setPassword(encoder.);
        return new org.springframework.security.core.userdetails.User(userDto.getUsername(), userDto.getPassword(), getAuthority(userDto));
    }

    private Set<SimpleGrantedAuthority> getAuthority(UserDto userDto) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        if (userDto.getRoles() != null) {
            for (RoleDto role: userDto.getRoles()) {
                authorities.add(new SimpleGrantedAuthority(PREFIX + role.getName()));
            }
        }
        return authorities;
    }
}
