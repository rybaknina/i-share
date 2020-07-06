package eu.senla.course.controller;

import eu.senla.course.annotation.di.Injection;
import eu.senla.course.api.service.IGarageService;
import eu.senla.course.entity.Garage;
import eu.senla.course.entity.Order;
import eu.senla.course.entity.Spot;
import eu.senla.course.exception.ServiceException;

import java.time.LocalDateTime;
import java.util.List;

public class GarageController {
    @Injection
    private static IGarageService service;
    private final static GarageController instance = new GarageController();

    private GarageController() {

    }

    public static GarageController getInstance(){
        return instance;
    }

    public void addGarage(Garage garage) throws ServiceException {
        service.addGarage(garage);
    }
    public void setGarages(List<Garage> garages){
        service.setGarages(garages);
    }
    public List<Garage> getGarages(){
        return service.getGarages();
    }
    public Garage getGarageById(int id) throws ServiceException {
        return service.getGarageById(id);
    }
    public void deleteGarage(Garage garage) throws ServiceException {
        service.deleteGarage(garage);
    }
    public int lengthAllSpots(){
        return service.lengthAllSpots();
    }
    public List<Spot> listAvailableSpots(LocalDateTime futureDate, List<Order> orders) throws ServiceException {
        return service.listAvailableSpots(futureDate, orders);
    }
    public int numberAvailableSpots(LocalDateTime futureDate, List<Order> orders) throws ServiceException {
        return service.numberAvailableSpots(futureDate, orders);
    }
    public void garagesFromCsv() throws ServiceException{
        service.garagesFromCsv();
    }
    public void garagesToCsv(){
        service.garagesToCsv();
    }
}
