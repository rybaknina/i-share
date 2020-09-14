package eu.senla.course.controller;

import eu.senla.course.api.service.IMechanicService;
import eu.senla.course.entity.Mechanic;
import eu.senla.course.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
final public class MechanicController {

    private IMechanicService service;
    private static MechanicController instance;

    private MechanicController() {
    }

    public static MechanicController getInstance() {
        if (instance == null) {
            instance = new MechanicController();
        }
        return instance;
    }
    @Autowired
    public void setController(MechanicController controller) {
        this.instance = controller;
    }

    @Autowired
    public void setService(IMechanicService service) {
        this.service = service;
    }

    public void addMechanic(Mechanic mechanic) throws ServiceException {
        service.addMechanic(mechanic);
    }
    public List<Mechanic> getMechanics() {
        return service.getMechanics();
    }
    public void setMechanics(List<Mechanic> mechanics) {
        service.setMechanics(mechanics);
    }
    public void deleteMechanic(int id) {
        service.deleteMechanic(id);
    }
    public void updateMechanic(Mechanic mechanic) throws ServiceException {
        service.updateMechanic(mechanic);
    }
    public Mechanic getMechanicById(int id) {
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
    public void mechanicsToCsv() {
        service.mechanicsToCsv();
    }
}
