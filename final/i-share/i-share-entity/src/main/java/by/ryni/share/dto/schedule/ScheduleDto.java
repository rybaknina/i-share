package by.ryni.share.dto.schedule;

import by.ryni.share.dto.base.AbstractDto;
import by.ryni.share.dto.lesson.LessonShortDto;

import java.sql.Timestamp;

public class ScheduleDto extends AbstractDto {
    private Timestamp startDate;
    private byte period;
    private LessonShortDto lesson;

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

    public LessonShortDto getLesson() {
        return lesson;
    }

    public void setLesson(LessonShortDto lesson) {
        this.lesson = lesson;
    }
}
