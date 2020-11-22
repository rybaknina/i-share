package by.ryni.share.dto.role;

import by.ryni.share.dto.base.AbstractDto;
import org.springframework.security.core.GrantedAuthority;

public class RoleDto extends AbstractDto implements GrantedAuthority {
    private String name;

    public RoleDto() {
    }

    public RoleDto(String name) {
        this.name = name;
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
