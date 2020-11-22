package by.ryni.share.dto.chapter;

import by.ryni.share.dto.base.AbstractShortDto;

public class ChapterShortDto extends AbstractShortDto {
    private String name;
    private String description;

    public ChapterShortDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
