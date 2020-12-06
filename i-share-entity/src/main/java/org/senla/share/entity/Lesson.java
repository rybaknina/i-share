package org.senla.share.entity;

import org.hibernate.annotations.DynamicUpdate;
import org.senla.share.enums.LessonType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@DynamicUpdate
@Table(name = "lesson")
public class Lesson extends AbstractEntity {
    private String title;
    @Lob
    private String content;
    @Enumerated(EnumType.STRING)
    private LessonType type;
    private byte level;
    private Boolean active;

    @OneToMany(mappedBy = "lesson", orphanRemoval = true)
    private Set<Feedback> feedbacks = new HashSet<>();

    @OneToMany(mappedBy = "lesson", orphanRemoval = true)
    private Set<Schedule> schedules = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", referencedColumnName = "id", nullable = false, updatable = false)
    private Course course;

    public Lesson() {
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

    public LessonType getType() {
        return type;
    }

    public void setType(LessonType type) {
        this.type = type;
    }

    public byte getLevel() {
        return level;
    }

    public void setLevel(byte level) {
        this.level = level;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Set<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(Set<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public Set<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(Set<Schedule> schedules) {
        this.schedules = schedules;
    }
}
