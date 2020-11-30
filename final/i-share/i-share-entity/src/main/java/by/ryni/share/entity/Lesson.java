package by.ryni.share.entity;

import by.ryni.share.enums.LessonType;

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
    private Boolean active;

    @Column(name = "course_id", nullable = false)
    private int courseId;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", updatable = false, insertable = false)
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

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public Set<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(Set<Schedule> schedules) {
        this.schedules = schedules;
    }
}
