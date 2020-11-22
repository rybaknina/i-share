package eu.senla.course.api.service;

import eu.senla.course.dto.garage.GarageDto;
import eu.senla.course.dto.spot.SpotDto;
import eu.senla.course.dto.spot.SpotShortDto;
import eu.senla.course.entity.Spot;
import eu.senla.course.exception.ServiceException;

import java.util.List;

public interface ISpotService {
    Spot spotDtoToEntity(SpotDto spotDto);
    Spot spotShortDtoToEntity(SpotShortDto spotShortDto);
    List<SpotDto> getSpots();
    boolean isModifySpot();
    void setSpots(List<SpotDto> spotDtoList);
    void addSpot(SpotDto spotDto) throws ServiceException;
    SpotDto getSpotById(int id);
    void deleteSpot(int id);
    void updateSpot(SpotDto spotDto) throws ServiceException;
    List<SpotDto> spotsInGarage(GarageDto garageDto);
    void spotsFromCsv() throws ServiceException;
    void spotsToCsv();
}
