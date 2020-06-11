package eu.senla.course.controller;

import eu.senla.course.entity.Spot;

import java.util.ArrayList;
import java.util.List;

public class SpotManager {
    List<Spot> spots;

    public SpotManager() {
        this.spots = new ArrayList<>();
    }

    public List<Spot> getSpots() {
        return spots;
    }

    public void setSpots(List<Spot> spots) {
        this.spots = spots;
    }

    public void addSpot(Spot spot){
        spots.add(spot);
    }

    public Spot getSpotById(int id){
        return (spots == null)? null: spots.get(id);
    }

    public void deleteSpot(Spot spot){
        spots.removeIf(e -> e.equals(spot));
    }
}
