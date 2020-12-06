package org.senla.share.mapper;

import org.senla.share.dto.UserDto;
import org.senla.share.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface UserMapper extends GenericMapper<UserDto, User> {
    UserMapper instance = Mappers.getMapper(UserMapper.class);
}
