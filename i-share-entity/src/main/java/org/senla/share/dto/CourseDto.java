package org.senla.share.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.senla.share.enums.DonateType;

import javax.validation.constraints.*;
import java.math.BigDecimal;

public class CourseDto extends AbstractDto {
    @NotEmpty
    @Size(min = 5, max = 255, message = "{course.name.bad.size}")
    private String title;
    private String content;
    @Digits(integer = 1, fraction = 0, message = "{course.level.not.valid}")
    @Min(0)
    @Max(9)
    private Byte level;
    @Min(0)
    @Max(127)
    private Byte limitMembers;
    private DonateType donateType;
    private BigDecimal amount;
    private Boolean active = true;

    @JsonProperty("theme")
    private ThemeDto theme;
    @JsonIgnore
    private UserDto owner;

    public CourseDto() {
    }

    @JsonCreator
    public CourseDto(int id) {
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

    public Byte getLevel() {
        return level;
    }

    public void setLevel(Byte level) {
        this.level = level;
    }

    public Byte getLimitMembers() {
        return limitMembers;
    }

    public void setLimitMembers(Byte limitMembers) {
        this.limitMembers = limitMembers;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public DonateType getDonateType() {
        return donateType;
    }

    public void setDonateType(DonateType donateType) {
        this.donateType = donateType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @JsonIgnore
    public ThemeDto getTheme() {
        return theme;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public void setTheme(ThemeDto theme) {
        this.theme = theme;
    }

    public UserDto getOwner() {
        return owner;
    }

    public void setOwner(UserDto owner) {
        this.owner = owner;
    }
}
