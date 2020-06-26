package eu.senla.course.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Garage {
    private static final AtomicInteger count = new AtomicInteger(0);
    private int id;
    private String name;
    private List<Spot> spots = new ArrayList<>();
    private List<Mechanic> mechanics = new ArrayList<>();

    public Garage() {
        this.id = count.incrementAndGet();
    }

    public Garage(String name) {
        this();
        this.name = name;
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

    public List<Spot> getSpots() {
        return spots;
    }

    public void setSpots(List<Spot> spots) {
        this.spots = spots;
    }

    public List<Mechanic> getMechanics() {
        return mechanics;
    }

    public void setMechanics(List<Mechanic> mechanics) {
        this.mechanics = mechanics;
    }

    @Override
    public String toString() {
        return "Garage{" +
                "id = " + id +
                ", name = '" + name + '\'' +
                '}';
    }
}
