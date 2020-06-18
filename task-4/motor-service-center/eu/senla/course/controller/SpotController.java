package eu.senla.course.controller;

import eu.senla.course.api.ISpotService;
import eu.senla.course.entity.Spot;
import eu.senla.course.service.SpotService;

import java.util.List;

public class SpotController {

    private final static SpotController instance = new SpotController();
    private final ISpotService service = SpotService.getInstance();

    private SpotController() {

    }

    public static SpotController getInstance(){
        return instance;
    }

    public List<Spot> getSpots(){
        return service.getSpots();
    }
    public void setSpots(List<Spot> spots){
        service.setSpots(spots);
    }
    public void addSpot(Spot spot){
        service.addSpot(spot);
    }
    public Spot getSpotById(int id){
        return service.getSpotById(id);
    }
    public void deleteSpot(Spot spot){
        service.deleteSpot(spot);
    }
}
