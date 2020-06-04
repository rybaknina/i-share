package eu.senla.course.entity;

import java.util.Arrays;

public class Garage {
    private int id;
    private int capacity;
    private Spot[] spots;
    private Mechanic[] mechanics;

    public Garage(int id) {
        this.id = id;
        this.capacity = 1;
        this.spots = new Spot[capacity];
    }

    public Garage(int id, int capacity) {
        this.id = id;
        this.capacity = capacity;
        this.spots = new Spot[capacity];
    }
    public int getId() {
        return id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setSpots(Spot[] spots) {
        this.spots = spots;
    }

    public Spot[] getSpots() {
        return spots;
    }

    public Mechanic[] getMechanics() {
        return mechanics;
    }

    public void setMechanics(Mechanic[] mechanics) {
        this.mechanics = mechanics;
    }

    public void addSpot(Spot spot){
        if (spots[capacity - 1] != null) {
            System.out.println("Garage is full");
        }
        else {
            for (int i = 0; i < capacity; i++) {
                if (spots[i] == null) {
                    spots[i] = spot;
                    break;
                }
            }
        }
    }

    public void deleteSpot(int id){
        for (int i=0; i<capacity; i++){
            if (spots[i].getId() == id){
                spots[i] = null;
                break;
            }
        }
    }

    @Override
    public String toString() {
        return "Garage{" +
                "id = " + id +
                ", capacity = " + capacity +
                ", spots = " + Arrays.toString(spots) +
                '}';
    }
}
