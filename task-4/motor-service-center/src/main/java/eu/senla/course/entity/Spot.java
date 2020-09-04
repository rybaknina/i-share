package eu.senla.course.entity;

import eu.senla.course.api.entity.IEntity;

import javax.persistence.*;
import java.util.concurrent.atomic.AtomicInteger;

@Entity
@Table(name = "spot")
public class Spot implements IEntity {
    private static final long serialVersionUID = 5407284135064833379L;

    private static final AtomicInteger count = new AtomicInteger(0);
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "garage_id")
    private Garage garage;

    public Spot() {

    }

    public Spot(Garage garage) {
        this.id = count.incrementAndGet();
        this.garage = garage;
    }

    public Spot(int id, Garage garage) {
        this.id = id;
        this.garage = garage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static AtomicInteger getCount() {
        return count;
    }

    public Garage getGarage() {
        return garage;
    }

    public void setGarage(Garage garage) {
        this.garage = garage;
    }

    @Override
    public String toString() {
        return "Spot{" +
                "id = " + id +
                ", garage = " + garage +
                '}';
    }
}
