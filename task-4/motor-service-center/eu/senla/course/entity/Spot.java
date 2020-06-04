package eu.senla.course.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Spot {
    private int id;
    private LocalDateTime reservedDate;

    public Spot(int id) {
        this.id = id;
    }

    public Spot(int id, LocalDateTime reservedDate) {
        this.id = id;
        this.reservedDate = reservedDate;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getReservedDate() {
        return reservedDate;
    }

    public void setReservedDate(LocalDateTime reservedDate) {
        this.reservedDate = reservedDate;
    }

    @Override
    public String toString() {
        return "Spot{" +
                "id=" + id +
                ", reservedDate=" + ((reservedDate==null)?"":reservedDate.format(DateTimeFormatter.ofPattern("dd.mm.yyyy hh:mm:ss"))) +
                '}';
    }
}
