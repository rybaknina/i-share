package eu.senla.course.api;

import eu.senla.course.entity.Mechanic;
import eu.senla.course.service.ServiceException;

import java.util.Comparator;
import java.util.List;

public interface IMechanicService {
    void addMechanic(Mechanic mechanic);
    List<Mechanic> getMechanics();
    void setMechanics(List<Mechanic> mechanics);
    void deleteMechanic(Mechanic mechanic);
    Mechanic getMechanicById(int id);
    Mechanic firstFreeMechanic();
    void sortMechanicsBy(Comparator<Mechanic> comparator);
    void mechanicsFromCsv() throws ServiceException;
    void mechanicsToCsv() throws ServiceException;
}
