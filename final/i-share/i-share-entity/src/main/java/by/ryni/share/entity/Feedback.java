package by.ryni.share.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "feedback")
public class Feedback extends AbstractEntity {
    @Column(columnDefinition = "TEXT")
    private String text;
    @Column(name = "posted_date", nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp postedDate;
    @Column(name = "lesson_id", updatable = false)
    private int lessonId;
    @Column(name = "course_id", updatable = false)
    private int courseId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",  referencedColumnName = "id", nullable = false, updatable = false)
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

    public int getLessonId() {
        return lessonId;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
