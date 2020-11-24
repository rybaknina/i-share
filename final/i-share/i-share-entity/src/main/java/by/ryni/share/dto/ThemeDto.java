package by.ryni.share.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ThemeDto extends AbstractDto {
    private String name;
    private String description;
    private ChapterDto chapter;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UserDto owner;

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

    public ChapterDto getChapter() {
        return chapter;
    }

    public void setChapter(ChapterDto chapter) {
        this.chapter = chapter;
    }

    public UserDto getOwner() {
        return owner;
    }

    public void setOwner(UserDto owner) {
        this.owner = owner;
    }
}
