package by.ryni.share.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

public class UserDto extends AbstractDto {

    private String username;
    private String password;
    private String email;
    @JsonProperty
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date birthday;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Set<RoleDto> roles = new HashSet<>();
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean enabled = true;

    public UserDto() {
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public void setPassword(String password) {
        this.password = password;
    }

    public Set<RoleDto> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleDto> roles) {
        this.roles = roles;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
