package eu.senla.course.controller;

import eu.senla.course.api.IMechanicService;
import eu.senla.course.entity.Mechanic;
import eu.senla.course.service.MechanicService;
import eu.senla.course.exception.ServiceException;

import java.util.Comparator;
import java.util.List;

public class MechanicController {
    private final IMechanicService service = MechanicService.getInstance();
    private final static MechanicController instance = new MechanicController();

    private MechanicController() {
    }

    public static MechanicController getInstance(){
        return instance;
    }

    public void addMechanic(Mechanic mechanic) throws ServiceException {
        service.addMechanic(mechanic);
    }
    public List<Mechanic> getMechanics(){
        return service.getMechanics();
    }
    public void setMechanics(List<Mechanic> mechanics){
        service.setMechanics(mechanics);
    }
    public void deleteMechanic(Mechanic mechanic) throws ServiceException {
        service.deleteMechanic(mechanic);
    }
    public Mechanic getMechanicById(int id) throws ServiceException {
        return service.getMechanicById(id);
    }
    public Mechanic firstFreeMechanic() throws ServiceException {
        return service.firstFreeMechanic();
    }
    public void sortMechanicsBy(Comparator<Mechanic> comparator) throws ServiceException {
        service.sortMechanicsBy(comparator);
    }
    public void mechanicsFromCsv() throws ServiceException {
        service.mechanicsFromCsv();
    }
    public void mechanicsToCsv() throws ServiceException {
        service.mechanicsToCsv();
    }
}
