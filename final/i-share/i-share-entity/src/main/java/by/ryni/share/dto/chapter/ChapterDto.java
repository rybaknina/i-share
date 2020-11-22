package by.ryni.share.dto.chapter;

import by.ryni.share.dto.base.AbstractDto;
import by.ryni.share.dto.theme.ThemeShortDto;
import by.ryni.share.dto.user.UserShortDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashSet;
import java.util.Set;

public class ChapterDto extends AbstractDto {
    private String name;
    private String description;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Set<ThemeShortDto> themes = new HashSet<>();
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UserShortDto owner;
//    private int ownerId;

    public ChapterDto() {
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

    public Set<ThemeShortDto> getThemes() {
        return themes;
    }

    public void setThemes(Set<ThemeShortDto> themes) {
        this.themes = themes;
    }

    public UserShortDto getOwner() {
        return owner;
    }

    public void setOwner(UserShortDto owner) {
        this.owner = owner;
    }
}
