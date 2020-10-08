package eu.senla.course.api.service;

import eu.senla.course.dto.mechanic.MechanicDto;
import eu.senla.course.dto.mechanic.MechanicShortDto;
import eu.senla.course.entity.Mechanic;
import eu.senla.course.exception.ServiceException;

import java.util.Comparator;
import java.util.List;

public interface IMechanicService {
    Mechanic mechanicDtoToEntity(MechanicDto mechanicDto);
    Mechanic mechanicShortDtoToEntity(MechanicShortDto mechanicDto);
    void addMechanic(MechanicDto mechanicDto) throws ServiceException;
    List<MechanicDto> getMechanics();
    void setMechanics(List<MechanicDto> mechanicDtoList);
    void deleteMechanic(int id);
    void updateMechanic(MechanicDto mechanicDto) throws ServiceException;
    MechanicDto getMechanicById(int id);
    MechanicDto firstFreeMechanic() throws ServiceException;
    List<MechanicDto> sortMechanicsBy(Comparator<MechanicDto> comparator) throws ServiceException;
    void mechanicsFromCsv() throws ServiceException;
    void mechanicsToCsv();
}
