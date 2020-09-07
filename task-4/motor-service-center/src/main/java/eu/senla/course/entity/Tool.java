package eu.senla.course.entity;

import eu.senla.course.api.entity.IEntity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "tool")
public class Tool implements IEntity {
    private static final long serialVersionUID = 547711684159410719L;

    @Id
    private int id;
    private String name;
    private int hours;
    @Column(name = "hourly_price")
    private BigDecimal hourlyPrice;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    public Tool() {

    }

    public Tool(String name) {
        this.name = name;
        this.hours = 1;
        this.hourlyPrice = new BigDecimal(5);
    }

    public Tool(String name, int hours, BigDecimal hourlyPrice) {
        this.name = name;
        this.hours = hours;
        this.hourlyPrice = hourlyPrice;
    }

    public Tool(String name, int hours, BigDecimal hourlyPrice, Order order) {
        this.name = name;
        this.hours = hours;
        this.hourlyPrice = hourlyPrice;
        this.order = order;
    }

    public Tool(int id, String name, int hours, BigDecimal hourlyPrice, Order order) {
        this.id = id;
        this.name = name;
        this.hours = hours;
        this.hourlyPrice = hourlyPrice;
        this.order = order;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "Tool { " +
                "id = " + id +
                ", name = '" + name + '\'' +
                ", hours = " + hours +
                ", hourlyPrice = " + hourlyPrice +
                ", order = " + order +
                '}';
    }
}
