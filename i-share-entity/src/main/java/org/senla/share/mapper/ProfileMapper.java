package org.senla.share.mapper;

import org.senla.share.dto.ProfileDto;
import org.senla.share.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface ProfileMapper {
    ProfileMapper instance = Mappers.getMapper(ProfileMapper.class);

    ProfileDto entityToDto(User entity);

    User dtoToEntity(ProfileDto dto);

    User dtoToEntity(ProfileDto dto, @MappingTarget User user);
}
