package eu.senla.course.service;

import eu.senla.course.api.ISpotService;
import eu.senla.course.entity.Spot;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SpotService implements ISpotService {

    private final static ISpotService instance = new SpotService();

    private List<Spot> spots;

    private SpotService() {
        this.spots = new ArrayList<>();
    }

    public static ISpotService getInstance(){
        return instance;
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
        if (spots == null || spots.size() == 0){
            System.out.println("Spots are not exist");
            return null;
        }
        return spots.get(id);
    }

    public void deleteSpot(Spot spot){
        if (spots == null || spots.size() == 0){
            System.out.println("Spots are not exist");
        } else {
            spots.removeIf(e -> e.equals(spot));
        }
    }
    public void updateSpot(int id, Spot spot) throws ServiceException {
        // Is it OK?
        Optional.of(spots).orElseThrow(() -> new ServiceException("Spots are not found"));
        Optional.of(spots.set(id, spot)).orElseThrow(() -> new ServiceException("Spot is not found"));;

    }
}
