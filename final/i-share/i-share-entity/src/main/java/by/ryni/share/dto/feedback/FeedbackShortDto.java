package by.ryni.share.dto.feedback;

import by.ryni.share.dto.base.AbstractShortDto;

import java.sql.Timestamp;

public class FeedbackShortDto extends AbstractShortDto {
    private String text;
    private Timestamp postedDate;

    public FeedbackShortDto() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(Timestamp postedDate) {
        this.postedDate = postedDate;
    }
}
