package org.senla.share.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class DonateDto extends AbstractDto {
    @DecimalMin(value = "0.1", message = "{donate.donation.min}")
    @DecimalMax(value = "10000", message = "{donate.donation.max}")
    @Digits(integer = 17, fraction = 2)
    private BigDecimal donation;
    @NotNull
    @JsonProperty("course")
    private CourseDto course;
    @JsonIgnore
    private UserDto user;

    public DonateDto() {
    }

    @JsonCreator
    public DonateDto(int id) {
        super.setId(id);
    }

    public BigDecimal getDonation() {
        return donation;
    }

    public void setDonation(BigDecimal donation) {
        this.donation = donation;
    }

    @JsonIgnore
    public CourseDto getCourse() {
        return course;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public void setCourse(CourseDto course) {
        this.course = course;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
}
