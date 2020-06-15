package eu.senla.course.controller;

import eu.senla.course.api.IMechanicService;
import eu.senla.course.entity.Mechanic;

import java.util.Comparator;
import java.util.List;

public class MechanicController {
    private final IMechanicService service;

    public MechanicController(IMechanicService service) {
        this.service = service;
    }
    public void addMechanic(Mechanic mechanic){
        service.addMechanic(mechanic);
    }
    public List<Mechanic> getMechanics(){
        return service.getMechanics();
    }
    public void setMechanics(List<Mechanic> mechanics){
        service.setMechanics(mechanics);
    }
    public void deleteMechanic(Mechanic mechanic){
        service.deleteMechanic(mechanic);
    }
    public Mechanic gerMechanicById(int id){
        return service.gerMechanicById(id);
    }
    public Mechanic firstFreeMechanic(){
        return service.firstFreeMechanic();
    }
    public void sortMechanicsBy(Comparator<Mechanic> comparator){
        service.sortMechanicsBy(comparator);
    }
}
