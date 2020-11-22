package by.ryni.share.dto.theme;

import by.ryni.share.dto.base.AbstractDto;
import by.ryni.share.dto.chapter.ChapterShortDto;
import by.ryni.share.dto.user.UserShortDto;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ThemeDto extends AbstractDto {
    private String name;
    private String description;
    private ChapterShortDto chapter;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UserShortDto owner;

    public ThemeDto() {
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

    public ChapterShortDto getChapter() {
        return chapter;
    }

    public void setChapter(ChapterShortDto chapter) {
        this.chapter = chapter;
    }

    public UserShortDto getOwner() {
        return owner;
    }

    public void setOwner(UserShortDto owner) {
        this.owner = owner;
    }
}
