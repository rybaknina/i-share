package eu.senla.course.controller;

import eu.senla.course.api.IGarageService;
import eu.senla.course.entity.Garage;
import eu.senla.course.entity.Order;
import eu.senla.course.entity.Spot;
import eu.senla.course.service.GarageService;

import java.time.LocalDateTime;
import java.util.List;

public class GarageController {
    private final IGarageService service = GarageService.getInstance();
    private final static GarageController instance = new GarageController();

    private GarageController() {

    }

    public static GarageController getInstance(){
        return instance;
    }

    public void addGarage(Garage garage){
        service.addGarage(garage);
    }
    public void setGarages(List<Garage> garages){
        service.setGarages(garages);
    }
    public List<Garage> getGarages(){
        return service.getGarages();
    }
    public Garage getGarageById(int id){
        return service.getGarageById(id);
    }
    public void deleteGarage(Garage garage){
        service.deleteGarage(garage);
    }
    public int lengthAllSpots(){
        return service.lengthAllSpots();
    }
    public List<Spot> listAvailableSpots(LocalDateTime futureDate, List<Order> orders){
        return service.listAvailableSpots(futureDate, orders);
    }
    public int numberAvailableSpots(LocalDateTime futureDate, List<Order> orders){
        return service.numberAvailableSpots(futureDate, orders);
    }
}