package eu.senla.course.api.service;

import eu.senla.course.dto.garage.GarageDto;
import eu.senla.course.dto.order.OrderDto;
import eu.senla.course.dto.spot.SpotDto;
import eu.senla.course.entity.Garage;
import eu.senla.course.exception.ServiceException;

import java.time.LocalDateTime;
import java.util.List;

public interface IGarageService {
    Garage garageDtoToEntity(GarageDto garageDto);
    void addGarage(GarageDto garageDto) throws ServiceException;
    void updateGarage(GarageDto garageDto) throws ServiceException;
    void setGarages(List<GarageDto> garageDtoList);
    List<GarageDto> getGarages();
    GarageDto getGarageById(int id);
    void deleteGarage(int id);
    int lengthAllSpots();
    List<SpotDto> listAvailableSpots(LocalDateTime futureDate, List<OrderDto> orderDtoList) throws ServiceException;
    int numberAvailableSpots(LocalDateTime futureDate, List<OrderDto> orderDtoList) throws ServiceException;
    void garagesFromCsv() throws ServiceException;
    void garagesToCsv();
}
