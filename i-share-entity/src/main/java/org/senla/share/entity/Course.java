package org.senla.share.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.DynamicUpdate;
import org.senla.share.enums.DonateType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@DynamicUpdate
@Table(name = "course")
public class Course extends AbstractEntity {
    private String title;
    @Lob
    private String content;
    private Byte level;
    @Column(name = "limit_members")
    private Byte limitMembers;
    @Column(name = "donate_type")
    @Enumerated(EnumType.STRING)
    private DonateType donateType;
    private BigDecimal amount;
    private Boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theme_id", referencedColumnName = "id", nullable = false, updatable = false)
    private Theme theme;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_course",
            joinColumns = {@JoinColumn(name = "course_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")})
    private Set<User> members = new HashSet<>();

    @OneToMany(mappedBy = "course", orphanRemoval = true)
    private Set<Donate> donates = new HashSet<>();

    @OneToMany(mappedBy = "course", orphanRemoval = true)
    private Set<Feedback> feedbacks = new HashSet<>();

    @OneToMany(mappedBy = "course", orphanRemoval = true)
    private Set<Lesson> lessons = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", referencedColumnName = "id", nullable = false, updatable = false)
    @JsonBackReference
    private User owner;

    public Course() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Byte getLevel() {
        return level;
    }

    public void setLevel(Byte level) {
        this.level = level;
    }

    public Byte getLimitMembers() {
        return limitMembers;
    }

    public void setLimitMembers(Byte limitMembers) {
        this.limitMembers = limitMembers;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public DonateType getDonateType() {
        return donateType;
    }

    public void setDonateType(DonateType donateType) {
        this.donateType = donateType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Set<User> getMembers() {
        return members;
    }

    public void setMembers(Set<User> members) {
        this.members = members;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
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

    public Set<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(Set<Lesson> lessons) {
        this.lessons = lessons;
    }
}
