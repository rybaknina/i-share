package by.ryni.share.mapper;

import by.ryni.share.dto.user.UserDto;
import by.ryni.share.dto.user.UserShortDto;
import by.ryni.share.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel  = "spring")
@Component
public interface UserMapper extends GenericMapper<UserDto, UserShortDto, User> {
    UserMapper instance = Mappers.getMapper(UserMapper.class);
    @Mapping(target = "authorities", ignore = true)
    UserDto entityToDto(User user);
    @Mapping(target = "authorities", ignore = true)
    User dtoToEntity(UserDto userDto);
    User shortDtoToEntity(UserShortDto userShortDto);
    UserShortDto entityToShortDto(User user);
}
