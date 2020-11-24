package by.ryni.share.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "theme")
public class Theme extends AbstractEntity {
    private String name;
    @Column(columnDefinition = "MEDIUMTEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chapter_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private Chapter chapter;

    // TODO: Maybe rewrite to owner_id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id",  referencedColumnName = "id", nullable = false)
    @JsonBackReference
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
}
