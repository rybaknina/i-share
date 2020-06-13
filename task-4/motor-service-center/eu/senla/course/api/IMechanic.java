package eu.senla.course.api;

import eu.senla.course.entity.Mechanic;

import java.util.Comparator;
import java.util.List;

public interface IMechanic {
    void addMechanic(Mechanic mechanic);
    List<Mechanic> getMechanics();
    void setMechanics(List<Mechanic> mechanics);
    void deleteMechanic(Mechanic mechanic);
    Mechanic gerMechanicById(int id);
    Mechanic firstFreeMechanic();
    void sortMechanicsBy(Comparator<Mechanic> comparator);
}
