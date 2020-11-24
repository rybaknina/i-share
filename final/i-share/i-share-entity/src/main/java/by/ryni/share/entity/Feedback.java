package by.ryni.share.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "feedback")
public class Feedback extends AbstractEntity {
    @Column(columnDefinition = "TEXT")
    private String text;
    @Column(name = "posted_date")
    private Timestamp postedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id",  referencedColumnName = "id")
    @JsonBackReference
    private Lesson lesson;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id",  referencedColumnName = "id")
    @JsonBackReference
    private Course course;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",  referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private User user;

    public Feedback() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(Timestamp postedDate) {
        this.postedDate = postedDate;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
