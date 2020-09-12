package eu.senla.course.controller;

import eu.senla.course.api.service.IGarageService;
import eu.senla.course.entity.Garage;
import eu.senla.course.entity.Order;
import eu.senla.course.entity.Spot;
import eu.senla.course.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
final public class GarageController {

    private IGarageService service;
    private static GarageController instance;

    private GarageController() {
    }

    public static GarageController getInstance() {
        if (instance == null) {
            instance = new GarageController();
        }
        return instance;
    }
    @Autowired
    public void setController(GarageController controller) {
        this.instance = controller;
    }

    @Autowired
    public void setService(IGarageService service) {
        this.service = service;
    }

    public void addGarage(Garage garage) throws ServiceException {
        service.addGarage(garage);
    }
    public void setGarages(List<Garage> garages) {
        service.setGarages(garages);
    }
    public List<Garage> getGarages() {
        return service.getGarages();
    }
    public Garage getGarageById(int id) {
        return service.getGarageById(id);
    }
    public void deleteGarage(int id) {
        service.deleteGarage(id);
    }
    public int lengthAllSpots() {
        return service.lengthAllSpots();
    }
    public List<Spot> listAvailableSpots(LocalDateTime futureDate, List<Order> orders) throws ServiceException {
        return service.listAvailableSpots(futureDate, orders);
    }
    public int numberAvailableSpots(LocalDateTime futureDate, List<Order> orders) throws ServiceException {
        return service.numberAvailableSpots(futureDate, orders);
    }
    public void garagesFromCsv() throws ServiceException {
        service.garagesFromCsv();
    }
    public void garagesToCsv() {
        service.garagesToCsv();
    }
}
