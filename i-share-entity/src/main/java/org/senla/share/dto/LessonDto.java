package org.senla.share.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.senla.share.enums.LessonType;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class LessonDto extends AbstractDto {
    @NotEmpty
    @Size(min = 5, max = 255, message = "{lesson.name.bad.size}")
    private String title;
    private String content;
    private LessonType type;
    @Digits(integer = 1, fraction = 0, message = "{lesson.level.not.valid}")
    private byte level;
    private Boolean active = true;
    @JsonProperty("course")
    private CourseDto course;

    public LessonDto() {
    }

    @JsonCreator
    public LessonDto(int id) {
        super.setId(id);
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

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @JsonIgnore
    public CourseDto getCourse() {
        return course;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public void setCourse(CourseDto course) {
        this.course = course;
    }
}
