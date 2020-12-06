package org.senla.share.entity;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Date;
import java.util.*;

@Entity
@DynamicUpdate
@Table(name = "user")
public class User extends AbstractEntity implements UserDetails {
    @Column(name = "username", nullable = false, updatable = false)
    private String username;
    private String password;
    private String email;
    private Date birthday;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private Set<Role> roles = new HashSet<>();
    private boolean enabled;

    @OneToMany(mappedBy = "owner", orphanRemoval = true)
    private Set<Chapter> chapters = new HashSet<>();
    @OneToMany(mappedBy = "owner", orphanRemoval = true)
    private Set<Course> courses = new HashSet<>();
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private Set<Donate> donates = new HashSet<>();
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private Set<Feedback> feedbacks = new HashSet<>();
    @OneToMany(mappedBy = "owner", orphanRemoval = true)
    private Set<Theme> themes = new HashSet<>();

    @Column(name = "account_non_expired")
    private boolean accountNonExpired = true;
    @Column(name = "account_non_locked")
    private boolean accountNonLocked = true;
    @Column(name = "credentials_non_expired")
    private boolean credentialsNonExpired = true;

    public User() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        for (Role role : this.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

    public Set<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(Set<Chapter> chapters) {
        this.chapters = chapters;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public Set<Donate> getDonates() {
        return donates;
    }

    public void setDonates(Set<Donate> donates) {
        this.donates = donates;
    }

    public Set<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(Set<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public Set<Theme> getThemes() {
        return themes;
    }

    public void setThemes(Set<Theme> themes) {
        this.themes = themes;
    }
}
