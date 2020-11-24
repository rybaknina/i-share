package by.ryni.share.entity;

import by.ryni.share.enums.LessonType;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "lesson")
public class Lesson extends AbstractEntity {
    private String title;
    @Lob
    private String content;
    @Enumerated(EnumType.STRING)
    private LessonType type;
    private byte level;
    private boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private Course course;

    @OneToMany //(mappedBy = "lesson", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    //@Cascade(org.hibernate.annotations.CascadeType.REPLICATE)
    @JoinColumn(name = "schedule_id")
    private Set<Schedule> schedules = new HashSet<>();

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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Set<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(Set<Schedule> schedules) {
        this.schedules = schedules;
    }
}
