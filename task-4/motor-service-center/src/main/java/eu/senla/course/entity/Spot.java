package eu.senla.course.entity;

import java.util.concurrent.atomic.AtomicInteger;

public class Spot {
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
