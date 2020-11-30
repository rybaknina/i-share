package by.ryni.share.mapper;

import by.ryni.share.dto.UserDto;
import by.ryni.share.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel  = "spring")
@Component
public interface UserMapper extends GenericMapper<UserDto, User> {
    UserMapper instance = Mappers.getMapper(UserMapper.class);
}
