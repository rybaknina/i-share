package by.ryni.share.dto.schedule;

import by.ryni.share.dto.base.AbstractShortDto;

import java.sql.Timestamp;

public class ScheduleShortDto extends AbstractShortDto {
    private Timestamp startDate;
    private byte period;

    public ScheduleShortDto() {
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public byte getPeriod() {
        return period;
    }

    public void setPeriod(byte period) {
        this.period = period;
    }
}
