package by.ryni.share.dto;

import by.ryni.share.enums.DonateType;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class CourseDto extends AbstractDto {
    private String title;
    private String content;
    private Byte level;
    private Byte limitMembers;
    private DonateType donateType;
    private BigDecimal amount;
    private boolean active;
    private ThemeDto theme;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UserDto owner;

    public CourseDto() {
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
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

    public ThemeDto getTheme() {
        return theme;
    }

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
