package eu.senla.course.dto.role;

import eu.senla.course.api.entity.IEntity;
import eu.senla.course.dto.permission.PermissionDto;
import eu.senla.course.entity.Permission;
import eu.senla.course.entity.Role;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public class RoleDto implements IEntity, GrantedAuthority {
    private int id;
    private String name;
    private List<PermissionDto> permissions = new ArrayList<>();

    public RoleDto() {
    }

    public RoleDto(Role role) {
        this.id = role.getId();
        this.name = role.getName();
        if (role.getPermissions() != null) {
            for (Permission permission: role.getPermissions()) {
                this.permissions.add(new PermissionDto(permission));
            }
        }
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PermissionDto> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<PermissionDto> permissions) {
        this.permissions = permissions;
    }

    @Override
    public String getAuthority() {
        return name;
    }
}
