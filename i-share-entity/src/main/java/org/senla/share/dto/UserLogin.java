package org.senla.share.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;

public class UserLogin {
    @NotEmpty(message = "{user.username.not.empty}")
    private String username;
    @NotEmpty(message = "{user.password.not.empty}")
    private String password;

    public String getUsername() {
        return username;
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
}
