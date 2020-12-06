package org.senla.share.entity;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", referencedColumnName = "id", nullable = false, updatable = false)
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, updatable = false)
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
