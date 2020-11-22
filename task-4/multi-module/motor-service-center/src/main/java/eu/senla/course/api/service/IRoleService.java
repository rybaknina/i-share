package eu.senla.course.api.service;

import eu.senla.course.dto.role.RoleDto;
import eu.senla.course.entity.Role;
import eu.senla.course.exception.ServiceException;

import java.util.List;

public interface IRoleService {
    Role roleDtoToEntity(RoleDto roleDto);
    RoleDto loadRoleByName(String name);
    List<RoleDto> getRoles();
    void addRole(RoleDto roleDto) throws ServiceException;
    void updateRole(RoleDto roleDto) throws ServiceException;
    void deleteRole(int id);
}
