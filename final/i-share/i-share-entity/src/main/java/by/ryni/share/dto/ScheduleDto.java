package by.ryni.share.dto;

import java.sql.Timestamp;

public class ScheduleDto extends AbstractDto {
    private Timestamp startDate;
    private byte period;
    private LessonDto lesson;

    public ScheduleDto() {
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

    public LessonDto getLesson() {
        return lesson;
    }

    public void setLesson(LessonDto lesson) {
        this.lesson = lesson;
    }
}
