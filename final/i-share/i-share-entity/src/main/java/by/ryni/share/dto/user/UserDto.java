package by.ryni.share.dto.user;

import by.ryni.share.dto.base.AbstractDto;
import by.ryni.share.dto.chapter.ChapterShortDto;
import by.ryni.share.dto.role.RoleShortDto;
import by.ryni.share.dto.theme.ThemeShortDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Date;
import java.util.*;

public class UserDto extends AbstractDto implements UserDetails {

    private String username;
    private String password;
    private String email;
    @JsonProperty
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date birthday;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Set<ChapterShortDto> chapters = new HashSet<>();
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Set<ThemeShortDto> themes = new HashSet<>();
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Set<RoleShortDto> roles = new HashSet<>();
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean enabled = true;

    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;

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

    public Set<ChapterShortDto> getChapters() {
        return chapters;
    }

    public void setChapters(Set<ChapterShortDto> chapters) {
        this.chapters = chapters;
    }

    public Set<ThemeShortDto> getThemes() {
        return themes;
    }

    public void setThemes(Set<ThemeShortDto> themes) {
        this.themes = themes;
    }

    public Set<RoleShortDto> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleShortDto> roles) {
        this.roles = roles;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        for (RoleShortDto role: this.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }
}
