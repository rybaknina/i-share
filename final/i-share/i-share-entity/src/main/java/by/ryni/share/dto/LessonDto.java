package by.ryni.share.dto;

import by.ryni.share.enums.LessonType;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LessonDto extends AbstractDto {
    @NotEmpty
    @Size(min = 10, max = 255, message = "{lesson.name.bad.size}")
    private String title;
    @NotEmpty
    private String content;
    private LessonType type = LessonType.THEORY;
    @Digits(integer = 1, fraction = 0, message = "{lesson.level.not.valid}")
    private byte level = 0;
    private Boolean active = true;
    @NotNull
    private int courseId;

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

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
