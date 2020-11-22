package by.ryni.share.dto.lesson;

import by.ryni.share.dto.base.AbstractShortDto;
import by.ryni.share.enums.LessonType;

public class LessonShortDto extends AbstractShortDto {
    private String title;
    private String content;
    private LessonType type;
    private byte level;
    private boolean active;

    public LessonShortDto() {
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
}
