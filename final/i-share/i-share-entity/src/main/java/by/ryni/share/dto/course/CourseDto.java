package by.ryni.share.dto.course;

import by.ryni.share.dto.base.AbstractDto;
import by.ryni.share.dto.donate.DonateShortDto;
import by.ryni.share.dto.feedback.FeedbackShortDto;
import by.ryni.share.dto.lesson.LessonShortDto;
import by.ryni.share.dto.theme.ThemeShortDto;
import by.ryni.share.dto.user.UserShortDto;
import by.ryni.share.enums.DonateType;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CourseDto extends AbstractDto {
    private String title;
    private String content;
    private Byte level;
    private Byte limitMembers;
    private DonateType donateType;
    private BigDecimal amount;
    private boolean active;
    private ThemeShortDto theme;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Set<LessonShortDto> lessons = new HashSet<>();
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<UserShortDto> members = new ArrayList<>();
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<DonateShortDto> donates = new ArrayList<>();
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<FeedbackShortDto> feedbacks = new ArrayList<>();
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UserShortDto owner;

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

    public ThemeShortDto getTheme() {
        return theme;
    }

    public void setTheme(ThemeShortDto theme) {
        this.theme = theme;
    }

    public UserShortDto getOwner() {
        return owner;
    }

    public void setOwner(UserShortDto owner) {
        this.owner = owner;
    }

    public Set<LessonShortDto> getLessons() {
        return lessons;
    }

    public void setLessons(Set<LessonShortDto> lessons) {
        this.lessons = lessons;
    }

    public List<UserShortDto> getMembers() {
        return members;
    }

    public void setMembers(List<UserShortDto> members) {
        this.members = members;
    }

    public List<DonateShortDto> getDonates() {
        return donates;
    }

    public void setDonates(List<DonateShortDto> donates) {
        this.donates = donates;
    }

    public List<FeedbackShortDto> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<FeedbackShortDto> feedbacks) {
        this.feedbacks = feedbacks;
    }
}
