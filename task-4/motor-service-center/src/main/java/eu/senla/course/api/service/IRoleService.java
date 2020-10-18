package eu.senla.course.api.service;

import eu.senla.course.dto.role.RoleDto;
import eu.senla.course.entity.Role;

public interface IRoleService {
    Role roleDtoToEntity(RoleDto roleDto);
    RoleDto loadRoleByName(String name);
}
