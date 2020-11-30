package by.ryni.share.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "donate")
public class Donate extends AbstractEntity {
    @Column(name = "creation_date", nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp creationDate;
    @Column(nullable = false, columnDefinition = "DECIMAL(17,2)")
    private BigDecimal donation;
    @Column(name = "course_id", nullable = false, updatable = false)
    private int courseId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",  referencedColumnName = "id", nullable = false, updatable = false)
    @JsonBackReference
    private User user;

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
