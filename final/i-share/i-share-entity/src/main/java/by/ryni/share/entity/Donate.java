package by.ryni.share.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "donate")
public class Donate extends AbstractEntity {
    @Column(name = "creation_date")
    private Timestamp creationDate;
    private BigDecimal donation;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id",  referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private Course course;

    public Donate() {
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public BigDecimal getDonation() {
        return donation;
    }

    public void setDonation(BigDecimal donation) {
        this.donation = donation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
