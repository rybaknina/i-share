package org.senla.share.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

public class ScheduleDto extends AbstractDto {
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private Timestamp startDate;
    @Digits(integer = 2, fraction = 0)
    @Min(15)
    @Max(99)
    private byte period;
    @JsonProperty("lesson")
    private LessonDto lesson;

    public ScheduleDto() {
    }

    @JsonCreator
    public ScheduleDto(int id) {
        super.setId(id);
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

    @JsonIgnore
    public LessonDto getLesson() {
        return lesson;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public void setLesson(LessonDto lesson) {
        this.lesson = lesson;
    }
}
