package eu.senla.course.controller;

import eu.senla.course.annotation.di.Injection;
import eu.senla.course.api.service.ISpotService;
import eu.senla.course.entity.Spot;
import eu.senla.course.exception.ServiceException;

import java.util.List;

public class SpotController {
    @Injection
    private static ISpotService service;
    private final static SpotController instance = new SpotController();

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
    public void addSpot(Spot spot) throws ServiceException {
        service.addSpot(spot);
    }
    public boolean isModifySpot(){
        return service.isModifySpot();
    }
    public Spot getSpotById(int id) throws ServiceException {
        return service.getSpotById(id);
    }
    public void deleteSpot(Spot spot) throws ServiceException {
        service.deleteSpot(spot);
    }
    public void updateSpot(int id, Spot spot) throws ServiceException {
        service.updateSpot(spot);
    }
    public void spotsFromCsv() throws ServiceException{
        service.spotsFromCsv();
    }
    public void spotsToCsv() {
        service.spotsToCsv();
    }
}
