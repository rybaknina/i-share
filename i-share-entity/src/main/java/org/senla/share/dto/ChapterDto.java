package org.senla.share.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class ChapterDto extends AbstractDto {
    @NotEmpty
    @Size(min = 10, max = 255, message = "{chapter.name.bad.size}")
    private String name;
    private String description;
    @JsonIgnore
    private UserDto owner;

    public ChapterDto() {
    }

    @JsonCreator
    public ChapterDto(int id) {
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

    public UserDto getOwner() {
        return owner;
    }

    public void setOwner(UserDto owner) {
        this.owner = owner;
    }
}
