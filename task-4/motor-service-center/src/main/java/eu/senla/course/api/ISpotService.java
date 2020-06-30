package eu.senla.course.api;

import eu.senla.course.entity.Spot;
import eu.senla.course.exception.ServiceException;

import java.util.List;

public interface ISpotService {
    List<Spot> getSpots();
    boolean isModifySpot() throws ServiceException;
    void setSpots(List<Spot> spots);
    void addSpot(Spot spot) throws ServiceException;
    Spot getSpotById(int id) throws ServiceException;
    void deleteSpot(Spot spot) throws ServiceException;
    void updateSpot(Spot spot) throws ServiceException;
    void spotsFromCsv() throws ServiceException;
    void spotsToCsv() throws ServiceException;
}
