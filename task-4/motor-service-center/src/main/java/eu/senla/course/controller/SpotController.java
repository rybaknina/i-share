package eu.senla.course.controller;

import eu.senla.course.api.service.ISpotService;
import eu.senla.course.entity.Garage;
import eu.senla.course.entity.Spot;
import eu.senla.course.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
final public class SpotController {

    private ISpotService service;
    private static SpotController instance;

    private SpotController() {
    }

    public static SpotController getInstance() {
        if (instance == null) {
            instance = new SpotController();
        }
        return instance;
    }

    @Autowired
    public void setController(SpotController controller) {
        this.instance = controller;
    }
    @Autowired
    public void setService(ISpotService service) {
        this.service = service;
    }

    public List<Spot> getSpots() {
        return service.getSpots();
    }
    public void setSpots(List<Spot> spots) {
        service.setSpots(spots);
    }
    public void addSpot(Spot spot) throws ServiceException {
        service.addSpot(spot);
    }
    public boolean isModifySpot() {
        return service.isModifySpot();
    }
    public Spot getSpotById(int id) {
        return service.getSpotById(id);
    }
    public void deleteSpot(int id) {
        service.deleteSpot(id);
    }
    public void updateSpot(Spot spot) throws ServiceException {
        service.updateSpot(spot);
    }
    public List<Spot> spotsInGarage(Garage garage) {
        return service.spotsInGarage(garage);
    }
    public void spotsFromCsv() throws ServiceException {
        service.spotsFromCsv();
    }
    public void spotsToCsv() {
        service.spotsToCsv();
    }
}
