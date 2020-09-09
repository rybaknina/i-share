package eu.senla.course.api.service;

import eu.senla.course.entity.Garage;
import eu.senla.course.entity.Spot;
import eu.senla.course.exception.ServiceException;

import java.util.List;

public interface ISpotService {
    List<Spot> getSpots();
    boolean isModifySpot();
    void setSpots(List<Spot> spots);
    void addSpot(Spot spot) throws ServiceException;
    Spot getSpotById(int id);
    void deleteSpot(int id);
    void updateSpot(Spot spot) throws ServiceException;
    List<Spot> spotsInGarage(Garage garage);
    void spotsFromCsv() throws ServiceException;
    void spotsToCsv();
}
