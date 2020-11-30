package by.ryni.share.dto;

import by.ryni.share.enums.DonateType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.*;
import java.math.BigDecimal;

public class CourseDto extends AbstractDto {
    @NotEmpty
    @Size(min = 10, max = 255, message = "{course.name.bad.size}")
    private String title;
    @NotEmpty
    private String content;
    @Digits(integer = 1, fraction = 0, message = "{course.level.not.valid}")
    private Byte level = 0;
    @Min(0)
    @Max(127)
    private Byte limitMembers = 0;
    private DonateType donateType = DonateType.FREE;
    private BigDecimal amount = BigDecimal.ZERO;
    private Boolean active = true;
    @NotNull
    private int themeId;
    @JsonIgnore
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

    public int getThemeId() {
        return themeId;
    }

    public void setThemeId(int themeId) {
        this.themeId = themeId;
    }

    public UserDto getOwner() {
        return owner;
    }

    public void setOwner(UserDto owner) {
        this.owner = owner;
    }
}
