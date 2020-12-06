package org.senla.share.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "theme")
public class Theme extends AbstractEntity {
    private String name;
    @Column(columnDefinition = "MEDIUMTEXT", nullable = false)
    private String description;

    @OneToMany(mappedBy = "theme", orphanRemoval = true)
    private Set<Course> courses;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chapter_id", referencedColumnName = "id", nullable = false)
    private Chapter chapter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", referencedColumnName = "id", nullable = false, updatable = false)
    private User owner;

    public Theme() {
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

    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }
}
