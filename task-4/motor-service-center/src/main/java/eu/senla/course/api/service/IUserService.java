package eu.senla.course.api.service;

import eu.senla.course.dto.user.UserDto;
import eu.senla.course.entity.User;
import eu.senla.course.exception.ServiceException;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface IUserService extends UserDetailsService {
    User userDtoToEntity(UserDto userDto);
    void save(UserDto userDto) throws ServiceException;
    List<UserDto> getUsers();
    UserDto userById(int id);
    void deleteUser(int id);
    void updateUser(UserDto userDto) throws ServiceException;
}
