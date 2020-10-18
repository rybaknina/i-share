package eu.senla.course.dto.permission;

import eu.senla.course.api.entity.IEntity;
import eu.senla.course.entity.Permission;
import org.springframework.security.core.GrantedAuthority;

public class PermissionDto implements IEntity, GrantedAuthority {
    private int id;
    private String name;

    public PermissionDto() {
    }

    public PermissionDto(Permission permission) {
        this.id = permission.getId();
        this.name = permission.getName();
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

    @Override
    public String getAuthority() {
        return name;
    }
}
