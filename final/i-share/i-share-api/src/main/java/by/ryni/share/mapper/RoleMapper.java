package by.ryni.share.mapper;

import by.ryni.share.dto.role.RoleDto;
import by.ryni.share.dto.role.RoleShortDto;
import by.ryni.share.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel  = "spring")
@Component
public interface RoleMapper extends GenericMapper<RoleDto, RoleShortDto, Role> {
    RoleMapper instance = Mappers.getMapper(RoleMapper.class);
}
