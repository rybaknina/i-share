package by.ryni.share.entity;

import by.ryni.share.enums.DonateType;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
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

    @Column(name = "theme_id", nullable = false)
    private int themeId;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id", updatable = false, insertable = false)
    private Set<Lesson> lessons = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "user_course",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "course_id", referencedColumnName = "id")})
    private Set<User> members = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "donate_id", updatable = false, insertable = false)
    private List<Donate> donates = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "feedback_id", updatable = false, insertable = false)
    private List<Feedback> feedbacks = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id",  referencedColumnName = "id", nullable = false, updatable = false)
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

    public int getThemeId() {
        return themeId;
    }

    public void setThemeId(int themeId) {
        this.themeId = themeId;
    }

    public Set<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(Set<Lesson> lessons) {
        this.lessons = lessons;
    }

    public Set<User> getMembers() {
        return members;
    }

    public void setMembers(Set<User> members) {
        this.members = members;
    }

    public List<Donate> getDonates() {
        return donates;
    }

    public void setDonates(List<Donate> donates) {
        this.donates = donates;
    }

    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
