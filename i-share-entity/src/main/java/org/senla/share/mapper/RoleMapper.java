package org.senla.share.mapper;

import org.senla.share.dto.RoleDto;
import org.senla.share.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Component
public interface RoleMapper extends GenericMapper<RoleDto, Role> {
    RoleMapper instance = Mappers.getMapper(RoleMapper.class);
}
