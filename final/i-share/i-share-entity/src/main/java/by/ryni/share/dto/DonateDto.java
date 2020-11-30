package by.ryni.share.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class DonateDto extends AbstractDto {
    @DecimalMin(value = "0.10", message = "{donate.donation.min}")
    @DecimalMin(value = "1000.00", message = "{donate.donation.max}")
    @Digits(integer = 17, fraction = 2)
    private BigDecimal donation = BigDecimal.ZERO;
    @NotNull
    private int courseId;
    @JsonIgnore
    private UserDto user;

    public DonateDto() {
    }

    public BigDecimal getDonation() {
        return donation;
    }

    public void setDonation(BigDecimal donation) {
        this.donation = donation;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
}
