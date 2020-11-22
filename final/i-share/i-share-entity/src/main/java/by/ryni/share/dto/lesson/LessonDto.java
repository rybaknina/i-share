package by.ryni.share.dto.lesson;

import by.ryni.share.dto.base.AbstractDto;
import by.ryni.share.dto.course.CourseShortDto;
import by.ryni.share.dto.schedule.ScheduleShortDto;
import by.ryni.share.enums.LessonType;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashSet;
import java.util.Set;

public class LessonDto extends AbstractDto {
    private String title;
    private String content;
    private LessonType type;
    private byte level;
    private boolean active;
    private CourseShortDto course;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Set<ScheduleShortDto> schedules = new HashSet<>();

    public LessonDto() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LessonType getType() {
        return type;
    }

    public void setType(LessonType type) {
        this.type = type;
    }

    public byte getLevel() {
        return level;
    }

    public void setLevel(byte level) {
        this.level = level;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public CourseShortDto getCourse() {
        return course;
    }

    public void setCourse(CourseShortDto course) {
        this.course = course;
    }

    public Set<ScheduleShortDto> getSchedules() {
        return schedules;
    }

    public void setSchedules(Set<ScheduleShortDto> schedules) {
        this.schedules = schedules;
    }
}
