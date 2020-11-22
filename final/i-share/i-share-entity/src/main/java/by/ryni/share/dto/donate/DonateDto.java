package by.ryni.share.dto.donate;

import by.ryni.share.dto.base.AbstractDto;
import by.ryni.share.dto.course.CourseShortDto;
import by.ryni.share.dto.user.UserShortDto;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class DonateDto extends AbstractDto {
    private Timestamp creationDate;
    private BigDecimal donation;
    private UserShortDto user;
    private CourseShortDto course;

    public DonateDto() {
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public BigDecimal getDonation() {
        return donation;
    }

    public void setDonation(BigDecimal donation) {
        this.donation = donation;
    }

    public UserShortDto getUser() {
        return user;
    }

    public void setUser(UserShortDto user) {
        this.user = user;
    }

    public CourseShortDto getCourse() {
        return course;
    }

    public void setCourse(CourseShortDto course) {
        this.course = course;
    }
}
