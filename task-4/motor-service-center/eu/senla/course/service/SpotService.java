package eu.senla.course.service;

import eu.senla.course.api.ISpotService;
import eu.senla.course.entity.Spot;

import java.util.ArrayList;
import java.util.List;

public class SpotService implements ISpotService {
    List<Spot> spots;

    public SpotService() {
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
        if (spots == null){
            System.out.println("Spots are not exist");
            return null;
        }
        return spots.get(id);
    }

    public void deleteSpot(Spot spot){
        spots.removeIf(e -> e.equals(spot));
    }
}
