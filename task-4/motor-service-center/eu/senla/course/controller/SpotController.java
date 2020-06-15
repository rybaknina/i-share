package eu.senla.course.controller;

import eu.senla.course.api.ISpotService;
import eu.senla.course.entity.Spot;

import java.util.List;

public class SpotController {
    private final ISpotService service;

    public SpotController(ISpotService service) {
        this.service = service;
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
