package org.senla.share.entity;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "role")
public class Role extends AbstractEntity implements GrantedAuthority {
    private String name;

    public Role() {
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
