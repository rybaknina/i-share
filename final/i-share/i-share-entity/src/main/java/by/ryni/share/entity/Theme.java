package by.ryni.share.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "theme")
public class Theme extends AbstractEntity {
    private String name;
    @Column(columnDefinition = "MEDIUMTEXT", nullable = false)
    private String description;

    @Column(name = "chapter_id", nullable = false)
    private int chapterId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id",  referencedColumnName = "id", nullable = false, updatable = false)
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

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
