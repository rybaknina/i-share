package org.senla.share.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class ThemeDto extends AbstractDto {
    @NotEmpty
    @Size(min = 10, max = 255, message = "{theme.name.bad.size}")
    private String name;
    private String description;

    @JsonProperty("chapter")
    private ChapterDto chapter;
    @JsonIgnore
    private UserDto owner;

    public ThemeDto() {
    }

    @JsonCreator
    public ThemeDto(int id) {
        super.setId(id);
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

    @JsonIgnore
    public ChapterDto getChapter() {
        return chapter;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
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
