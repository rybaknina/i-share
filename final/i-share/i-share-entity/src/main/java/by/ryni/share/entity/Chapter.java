package by.ryni.share.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "chapter")
public class Chapter extends AbstractEntity {
    private String name;
    @Column(columnDefinition = "MEDIUMTEXT", nullable = false)
    private String description;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "theme_id", updatable = false, insertable = false)
    private Set<Theme> themes = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id",  referencedColumnName = "id", nullable = false, updatable = false)
    @JsonBackReference
    private User owner;

    public Chapter() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Theme> getThemes() {
        return themes;
    }

    public void setThemes(Set<Theme> themes) {
        this.themes = themes;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
