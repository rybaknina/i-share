package eu.senla.course.entity;

import java.util.ArrayList;
import java.util.List;

public class Garage {
    private int id;
    private List<Spot> spots = new ArrayList<>();
    private List<Mechanic> mechanics = new ArrayList<>();

    public Garage(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
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
                '}';
    }
}
