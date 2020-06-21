package eu.senla.course.api;

import eu.senla.course.entity.Garage;
import eu.senla.course.entity.Order;
import eu.senla.course.entity.Spot;
import eu.senla.course.service.ServiceException;

import java.time.LocalDateTime;
import java.util.List;

public interface IGarageService {
    void addGarage(Garage garage);
    void updateGarage(int id, Garage garage) throws ServiceException;
    void setGarages(List<Garage> garages);
    List<Garage> getGarages();
    Garage getGarageById(int id);
    void deleteGarage(Garage garage);
    int lengthAllSpots();
    List<Spot> listAvailableSpots(LocalDateTime futureDate, List<Order> orders);
    int numberAvailableSpots(LocalDateTime futureDate, List<Order> orders);
    void garagesFromCsv() throws ServiceException;
    void garagesToCsv() throws ServiceException;
}
