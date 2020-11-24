package by.ryni.share.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class DonateDto extends AbstractDto {
    private Timestamp creationDate;
    private BigDecimal donation;
    private UserDto user;
    private CourseDto course;

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

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public CourseDto getCourse() {
        return course;
    }

    public void setCourse(CourseDto course) {
        this.course = course;
    }
}
