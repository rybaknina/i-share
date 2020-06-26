package eu.senla.course.entity;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

public class Tool {
    private static final AtomicInteger count = new AtomicInteger(0);
    private int id;
    private String name;
    private int hours;
    private BigDecimal hourlyPrice;

    public Tool(String name) {
        this.id = count.incrementAndGet();
        this.name = name;
        this.hours = 1;
        this.hourlyPrice = new BigDecimal(5);
    }

    public Tool(String name, int hours, BigDecimal hourlyPrice) {
        this.id = count.incrementAndGet();
        this.name = name;
        this.hours = hours;
        this.hourlyPrice = hourlyPrice;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public BigDecimal getHourlyPrice() {
        return hourlyPrice;
    }

    public void setHourlyPrice(BigDecimal hourlyPrice) {
        this.hourlyPrice = hourlyPrice;
    }

    @Override
    public String toString() {
        return "Tool{" +
                "id = " + id +
                ", name = '" + name + '\'' +
                ", hours = " + hours +
                ", hourlyPrice = " + hourlyPrice +
                '}';
    }
}
