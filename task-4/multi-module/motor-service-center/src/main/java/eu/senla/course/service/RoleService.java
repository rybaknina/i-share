package eu.senla.course.service;

import eu.senla.course.api.repository.IRoleRepository;
import eu.senla.course.api.service.IRoleService;
import eu.senla.course.dto.permission.PermissionDto;
import eu.senla.course.dto.role.RoleDto;
import eu.senla.course.entity.Permission;
import eu.senla.course.entity.Role;
import eu.senla.course.exception.RepositoryException;
import eu.senla.course.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component("roleService")
public class RoleService implements IRoleService {
    private final static Logger logger = LogManager.getLogger(RoleService.class);
    private IRoleRepository roleRepository;

    @Autowired
    @Qualifier("roleHibernateRepository")
    public void setRoleRepository(IRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role roleDtoToEntity(RoleDto roleDto) {
        Role role = new Role();
        role.setId(roleDto.getId());
        role.setName(roleDto.getName());
        List<Permission> permissions = new ArrayList<>();
        if (roleDto.getPermissions() != null) {
            for (PermissionDto permissionDto: roleDto.getPermissions()) {
                Permission permission = new Permission();
                permission.setId(permissionDto.getId());
                permission.setName(permissionDto.getName());
                permissions.add(permission);
            }
        }
        role.setPermissions(permissions);
        return role;
    }
    @Transactional(readOnly = true)
    public RoleDto loadRoleByName(String name) {
        Role role = this.roleRepository.findByName(name);
        return new RoleDto(role);
    }

    @Transactional(readOnly = true)
    public List<RoleDto> getRoles() {
        return roleRepository
                .getAll()
                .stream()
                .map(RoleDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void addRole(RoleDto roleDto) throws ServiceException {
        try {
            roleRepository.add(roleDtoToEntity(roleDto));
        } catch (RepositoryException e) {
            throw new ServiceException("RepositoryException " + e.getMessage());
        }
    }

    @Transactional
    public void updateRole(RoleDto roleDto) throws ServiceException {
        try {
            roleRepository.update(roleDtoToEntity(roleDto));
        } catch (RepositoryException e) {
            throw new ServiceException("RepositoryException " + e.getMessage());
        }
    }

    @Transactional
    public void deleteRole(int id) {
        roleRepository.delete(id);
    }
}
