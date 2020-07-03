package eu.senla.course.controller;

import eu.senla.course.api.ISpotService;
import eu.senla.course.entity.Spot;
import eu.senla.course.exception.ServiceException;
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
    public void addSpot(Spot spot) throws ServiceException {
        service.addSpot(spot);
    }
    public boolean isModifySpot(){
        try {
            return service.isModifySpot();
        } catch (ServiceException e) {
            return false;
        }
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
    public void spotsToCsv() throws ServiceException{
        service.spotsToCsv();
    }
}
