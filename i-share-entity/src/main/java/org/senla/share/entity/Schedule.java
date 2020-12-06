package org.senla.share.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "schedule")
public class Schedule extends AbstractEntity {
    @Column(name = "start_date", nullable = false)
    private Timestamp startDate;
    @Column(name = "period", nullable = false)
    private byte period;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id", referencedColumnName = "id", nullable = false, updatable = false)
    private Lesson lesson;

    public Schedule() {
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public byte getPeriod() {
        return period;
    }

    public void setPeriod(byte period) {
        this.period = period;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }
}
