package org.senla.share.service.impl;

import org.senla.share.criteria.SearchCriteria;
import org.senla.share.dto.CourseDto;
import org.senla.share.dto.RoleDto;
import org.senla.share.dto.ThemeDto;
import org.senla.share.dto.UserDto;
import org.senla.share.entity.Course;
import org.senla.share.entity.Role;
import org.senla.share.entity.User;
import org.senla.share.enums.UserRole;
import org.senla.share.mapper.CourseMapper;
import org.senla.share.mapper.RoleMapper;
import org.senla.share.mapper.UserMapper;
import org.senla.share.repository.CourseRepository;
import org.senla.share.service.CourseService;
import org.senla.share.service.RoleService;
import org.senla.share.service.ThemeService;
import org.senla.share.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service("courseService")
public class DefaultCourseService extends AbstractService<CourseDto, Course, CourseRepository> implements CourseService {
    private CourseRepository courseRepository;
    private UserService userService;
    private RoleService roleService;
    private RoleMapper roleMapper;
    private CourseMapper courseMapper;
    private ThemeService themeService;
    private UserMapper userMapper;

    @Autowired
    public DefaultCourseService(@Qualifier("courseRepository") CourseRepository courseRepository, CourseMapper courseMapper) {
        super(courseRepository, courseMapper);
    }

    @Autowired
    public void setCourseRepository(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setRoleMapper(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Autowired
    public void setCourseMapper(CourseMapper courseMapper) {
        this.courseMapper = courseMapper;
    }

    @Autowired
    public void setThemeService(ThemeService themeService) {
        this.themeService = themeService;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Transactional(readOnly = true)
    @Override
    public List<CourseDto> search(List<SearchCriteria> params) {
        List<CourseDto> courseDtoList = courseRepository.search(params)
                .stream()
                .map(e -> courseMapper.entityToDto(e))
                .collect(Collectors.toList());
        return courseDtoList;
    }

    @Transactional
    @Override
    public Optional<CourseDto> save(CourseDto dto) {
        Optional<ThemeDto> theme = themeService.getById(dto.getTheme().getId());
        if (theme.isPresent()) {
            RoleDto userRole = roleService.findByName(UserRole.TRAINER.name()).get();
            UserDto user = userService.getById(dto.getOwner().getId()).get();
            if (user.getRoles().stream().map(RoleDto::getName).noneMatch(Predicate.isEqual(userRole.getName()))) {
                user.getRoles().add(userRole);
                userService.save(user);
            }
            Course course = courseMapper.dtoToEntity(dto);
            Optional<Course> optionalCourse = courseRepository.save(course);

            return Optional.of(courseMapper.entityToDto(optionalCourse.get()));
        } else {
            throw new EntityNotFoundException("Theme does not found");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public int availableSpace(int id) {
        return courseRepository.availableSpace(id);
    }

    @Transactional
    @Override
    public Boolean subscribe(int id, String username) {
        if (courseRepository.availableSpace(id) > 0) {
            Optional<Course> course = courseRepository.getById(id);
            if (course.isPresent()) {
                Set<User> members = course.get().getMembers();
                boolean find = members.stream().map(User::getUsername).anyMatch(Predicate.isEqual(username));
                if (!find) {
                    User user = userService.findByUsername(username).get();
                    Role userRole = roleMapper.dtoToEntity(roleService.findByName(UserRole.MEMBER.name()).get());
                    if (user.getRoles().stream().map(Role::getName).noneMatch(Predicate.isEqual(userRole.getName()))) {
                        user.getRoles().add(userRole);
                    }
                    members.add(user);
                    courseRepository.update(course.get());
                    return true;
                }
            }
        }
        return false;
    }

    @Transactional(readOnly = true)
    @Override
    public Set<UserDto> membersByCourse(int id) {
        Optional<Course> courseOptional = courseRepository.getById(id);
        HashSet<UserDto> userSet = new HashSet<>();
        if (courseOptional.isPresent()) {
            return courseRepository.membersByCourse(id)
                    .stream()
                    .map(e -> userMapper.entityToDto(e))
                    .collect(Collectors.toSet());
        }
        return userSet;
    }
}
