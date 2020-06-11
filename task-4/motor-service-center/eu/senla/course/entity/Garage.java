package eu.senla.course.entity;

import java.util.List;

public class Garage {
    private int id;
    private List<Spot> spots = null;
    private List<Mechanic> mechanics = null;

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

    // удалить место в гараже
    public void deleteSpot(Spot spot) {
        spots.removeIf(e -> e.equals(spot));
    }

    @Override
    public String toString() {
        return "Garage{" +
                "id = " + id +
                '}';
    }
}
