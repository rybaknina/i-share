package by.ryni.share.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "schedule")
public class Schedule extends AbstractEntity {
    @Column(name = "start_date", nullable = false)
    private Timestamp startDate;
    @Column(name = "period", nullable = false)
    private byte period = 30;

    @Column(name = "lesson_id", nullable = false)
    private int lessonId;

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

    public int getLessonId() {
        return lessonId;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }
}
