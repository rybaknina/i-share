package eu.senla.course.controller;

import eu.senla.course.api.service.IMechanicService;
import eu.senla.course.dto.mechanic.MechanicDto;
import eu.senla.course.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@RestController
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
    @Qualifier("mechanicService")
    public void setService(IMechanicService service) {
        this.service = service;
    }

    @PostMapping("/mechanic")
    public void addMechanic(@RequestBody MechanicDto mechanicDto) throws ServiceException {
        service.addMechanic(mechanicDto);
    }

    @GetMapping("/mechanics")
    public List<MechanicDto> getMechanics() {
        return service.getMechanics();
    }

    @PutMapping("/mechanics")
    public void setMechanics(@PathVariable List<MechanicDto> mechanicDtoList) {
        service.setMechanics(mechanicDtoList);
    }

    @DeleteMapping("/mechanic/{id}")
    public void deleteMechanic(@PathVariable int id) {
        service.deleteMechanic(id);
    }

    @PutMapping("/mechanic")
    public void updateMechanic(@RequestBody MechanicDto mechanicDto) throws ServiceException {
        service.updateMechanic(mechanicDto);
    }

    @GetMapping("/mechanic/{id}")
    public MechanicDto getMechanicById(@PathVariable int id) {
        return service.getMechanicById(id);
    }

    @GetMapping("/free_mechanic")
    public MechanicDto firstFreeMechanic() throws ServiceException {
        return service.firstFreeMechanic();
    }
    public void sortMechanicsBy(Comparator<MechanicDto> comparator) throws ServiceException {
        service.sortMechanicsBy(comparator);
    }
    public void mechanicsFromCsv() throws ServiceException {
        service.mechanicsFromCsv();
    }
    public void mechanicsToCsv() {
        service.mechanicsToCsv();
    }
}
