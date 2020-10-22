package eu.senla.course.service;

import eu.senla.course.api.repository.IPermissionRepository;
import eu.senla.course.api.service.IPermissionService;
import eu.senla.course.dto.permission.PermissionDto;
import eu.senla.course.entity.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("permissionService")
public class PermissionService implements IPermissionService {
    private IPermissionRepository permissionRepository;

    @Autowired
    @Qualifier("permissionHibernateRepository")
    public void setPermissionRepository(IPermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Transactional(readOnly = true)
    public PermissionDto loadRoleByName(String name) {
        Permission permission = this.permissionRepository.findByName(name);
        return new PermissionDto(permission);
    }
}
