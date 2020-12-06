package org.senla.share.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.sql.Date;
import java.util.Set;

public class UserDto extends AbstractDto {

    @NotEmpty(message = "{user.username.not.empty}")
    private String username;
    @NotEmpty(message = "{user.password.not.empty}")
    private String password;
    @Email(message = "{user.email.not.valid}")
    private String email;
    @JsonProperty
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date birthday;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Set<RoleDto> roles;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean enabled = true;

    public UserDto() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
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

    @JsonIgnore
    public void setRoles(Set<RoleDto> roles) {
        this.roles = roles;
    }

    public Boolean isEnabled() {
        return enabled;
    }
}
