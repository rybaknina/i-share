package by.ryni.share.dto.donate;

import by.ryni.share.dto.base.AbstractShortDto;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class DonateShortDto extends AbstractShortDto {
    private Timestamp creationDate;
    private BigDecimal donation;

    public DonateShortDto() {
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
}
