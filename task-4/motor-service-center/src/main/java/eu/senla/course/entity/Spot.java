package eu.senla.course.entity;

import eu.senla.course.api.entity.IEntity;

import java.util.concurrent.atomic.AtomicInteger;

public class Spot implements IEntity {
    private static final long serialVersionUID = 5407284135064833379L;

    private static final AtomicInteger count = new AtomicInteger(0);
    private int id;
    private Garage garage;

    public Spot(Garage garage) {
        this.id = count.incrementAndGet();
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
