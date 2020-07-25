package eu.senla.course.api.service;

import eu.senla.course.entity.Garage;
import eu.senla.course.entity.Order;
import eu.senla.course.entity.Spot;
import eu.senla.course.exception.ServiceException;

import java.time.LocalDateTime;
import java.util.List;

public interface IGarageService {
    void addGarage(Garage garage) throws ServiceException;
    void updateGarage(Garage garage) throws ServiceException;
    void setGarages(List<Garage> garages);
    List<Garage> getGarages();
    Garage getGarageById(int id);
    void deleteGarage(Garage garage);
    int lengthAllSpots();
    List<Spot> listAvailableSpots(LocalDateTime futureDate, List<Order> orders) throws ServiceException;
    int numberAvailableSpots(LocalDateTime futureDate, List<Order> orders) throws ServiceException;
    void garagesFromCsv() throws ServiceException;
    void garagesToCsv();
}
