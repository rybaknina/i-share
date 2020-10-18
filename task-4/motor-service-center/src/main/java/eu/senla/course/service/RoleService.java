package eu.senla.course.service;

import eu.senla.course.api.repository.IRoleRepository;
import eu.senla.course.api.service.IRoleService;
import eu.senla.course.dto.permission.PermissionDto;
import eu.senla.course.dto.role.RoleDto;
import eu.senla.course.entity.Permission;
import eu.senla.course.entity.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
}
