package by.ryni.share.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ThemeDto extends AbstractDto {
    @NotNull
    @Size(min = 10, max = 255, message = "{theme.name.bad.size}")
    private String name;
    private String description;
    @NotNull
    private int chapterId;
    @JsonIgnore
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

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public UserDto getOwner() {
        return owner;
    }

    public void setOwner(UserDto owner) {
        this.owner = owner;
    }
}
