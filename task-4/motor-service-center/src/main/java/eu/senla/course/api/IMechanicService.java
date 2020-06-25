package eu.senla.course.api;

import eu.senla.course.entity.Mechanic;
import eu.senla.course.exception.ServiceException;

import java.util.Comparator;
import java.util.List;

public interface IMechanicService {
    void addMechanic(Mechanic mechanic) throws ServiceException;
    List<Mechanic> getMechanics();
    void setMechanics(List<Mechanic> mechanics);
    void deleteMechanic(Mechanic mechanic) throws ServiceException;
    void updateMechanic(Mechanic mechanic) throws ServiceException;
    Mechanic getMechanicById(int id) throws ServiceException;
    Mechanic firstFreeMechanic() throws ServiceException;
    void sortMechanicsBy(Comparator<Mechanic> comparator) throws ServiceException;
    void mechanicsFromCsv() throws ServiceException;
    void mechanicsToCsv() throws ServiceException;
}
