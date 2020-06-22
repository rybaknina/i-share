package eu.senla.course.api;

import eu.senla.course.entity.Spot;
import eu.senla.course.service.ServiceException;

import java.util.List;

public interface ISpotService {
    List<Spot> getSpots();
    void setSpots(List<Spot> spots);
    void addSpot(Spot spot);
    Spot getSpotById(int id);
    void deleteSpot(Spot spot);
    void updateSpot(int id, Spot spot) throws ServiceException;
    void spotsFromCsv() throws ServiceException;
    void spotsToCsv() throws ServiceException;
}
