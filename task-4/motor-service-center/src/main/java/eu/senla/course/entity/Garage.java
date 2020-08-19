package eu.senla.course.entity;

import eu.senla.course.api.entity.IEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Garage implements IEntity {
    private static final long serialVersionUID = -2617930426404733166L;

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

    public Garage(int id, String name) {
        this.id = id;
        this.name = name;
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
