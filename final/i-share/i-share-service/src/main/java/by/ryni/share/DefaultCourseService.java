package by.ryni.share;

import by.ryni.share.api.*;
import by.ryni.share.criteria.SearchCriteria;
import by.ryni.share.dto.CourseDto;
import by.ryni.share.dto.RoleDto;
import by.ryni.share.entity.Course;
import by.ryni.share.entity.Role;
import by.ryni.share.entity.User;
import by.ryni.share.enums.UserRole;
import by.ryni.share.exception.RepositoryException;
import by.ryni.share.exception.ServiceException;
import by.ryni.share.mapper.CourseMapper;
import by.ryni.share.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("courseService")
public class DefaultCourseService extends AbstractService<CourseDto, Course, CourseRepository> implements CourseService {
    private CourseRepository courseRepository;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private RoleMapper roleMapper;
    private CourseMapper courseMapper;
    private ThemeRepository themeRepository;

    @Autowired
    public DefaultCourseService(@Qualifier("courseRepository") CourseRepository repository, CourseMapper mapper) {
        super(repository, mapper);
    }

    @Autowired
    public void setCourseRepository(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
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
    public void setThemeRepository(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
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
    public void save(CourseDto dto) throws ServiceException {
        if (!themeRepository.getById(dto.getThemeId()).isPresent()) {
            throw new ServiceException("Theme does not exists");
        }
        RoleDto userRole = roleMapper.entityToDto(roleRepository.findByName(UserRole.TRAINER.name()).get());
        dto.getOwner().getRoles().add(userRole);
        super.save(dto);
    }

    @Transactional(readOnly = true)
    @Override
    public int availableSpace(int id) {
        return courseRepository.availableSpace(id);
    }

    @Transactional
    @Override
    public void subscribe(int id, String username) throws ServiceException {
        if (courseRepository.availableSpace(id) > 0) {
            Optional<Course> course = courseRepository.getById(id);
            if (!course.isPresent()) {
                throw new ServiceException("Course does not exists");
            }

            User user = userRepository.findByUsername(username).get();
            if (course.get().getMembers().stream().anyMatch(e -> e.getUsername().equals(username))) {
                throw new ServiceException("You are already subscribed");
            }

            Role userRole = roleRepository.findByName(UserRole.MEMBER.name()).get();
            user.getRoles().add(userRole);
            course.get().getMembers().add(user);
            try {
                courseRepository.update(course.get());
            } catch (RepositoryException e) {
                throw new ServiceException("Repository exception " + e.getMessage());
            }
        } else {
            throw new ServiceException("You can not subscribe to this course");
        }
    }
}
