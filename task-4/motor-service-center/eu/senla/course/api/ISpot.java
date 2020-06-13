package eu.senla.course.api;

import eu.senla.course.entity.Spot;

import java.util.List;

public interface ISpot {
    List<Spot> getSpots();
    void setSpots(List<Spot> spots);
    void addSpot(Spot spot);
    Spot getSpotById(int id);
    void deleteSpot(Spot spot);
}
