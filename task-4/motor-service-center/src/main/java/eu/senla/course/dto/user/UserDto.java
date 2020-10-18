package eu.senla.course.dto.user;

import eu.senla.course.api.entity.IEntity;
import eu.senla.course.dto.permission.PermissionDto;
import eu.senla.course.dto.role.RoleDto;
import eu.senla.course.entity.Role;
import eu.senla.course.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDto implements IEntity, UserDetails {
    private int id;
    private String username;
    private String password;
    private boolean active;
    private List<RoleDto> roleDtoList = new ArrayList<>();

    public UserDto() {
    }

    public UserDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.active = user.isActive();
        if (user.getRoles() != null) {
            for (Role role: user.getRoles()) {
                roleDtoList.add(new RoleDto(role));
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<RoleDto> getRoleDtoList() {
        return roleDtoList;
    }

    public void setRoleDtoList(List<RoleDto> roleDtoList) {
        this.roleDtoList = roleDtoList;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (RoleDto role: this.getRoleDtoList()) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
            for (PermissionDto permission : role.getPermissions()) {
                authorities.add(new SimpleGrantedAuthority(permission.getName()));
            }
        }
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}
