package eu.senla.course.controller;

import eu.senla.course.api.service.IMechanicService;
import eu.senla.course.dto.mechanic.MechanicDto;
import eu.senla.course.entity.comparator.mechanic.ByAlphabet;
import eu.senla.course.entity.comparator.mechanic.ByBusy;
import eu.senla.course.enums.MechanicComparator;
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

    @PostMapping("/mechanics")
    public void addMechanic(@RequestBody MechanicDto mechanicDto) throws ServiceException {
        service.addMechanic(mechanicDto);
    }

    @GetMapping("/mechanics")
    public List<MechanicDto> getMechanics() {
        return service.getMechanics();
    }

    @PatchMapping("/mechanics")
    public void setMechanics(@PathVariable List<MechanicDto> mechanicDtoList) {
        service.setMechanics(mechanicDtoList);
    }

    @DeleteMapping("/mechanics/{id}")
    public void deleteMechanic(@PathVariable int id) {
        service.deleteMechanic(id);
    }

    @PutMapping("/mechanics")
    public void updateMechanic(@RequestBody MechanicDto mechanicDto) throws ServiceException {
        service.updateMechanic(mechanicDto);
    }

    @GetMapping("/mechanics/{id}")
    public MechanicDto getMechanicById(@PathVariable int id) {
        return service.getMechanicById(id);
    }

    @GetMapping("/mechanics/free")
    public MechanicDto firstFreeMechanic() throws ServiceException {
        return service.firstFreeMechanic();
    }

    @GetMapping("/mechanics/sort")
    public List<MechanicDto> sortMechanicsBy(@RequestParam(value = "sort", defaultValue = "BY_ALPHABET") String sortBy) throws ServiceException {
        Comparator<MechanicDto> dtoComparator = getMechanicComparator(sortBy);
        return service.sortMechanicsBy(dtoComparator);
    }

    private Comparator<MechanicDto> getMechanicComparator(String comparator) {
        comparator = comparator.toUpperCase();
        MechanicComparator mechanicComparator;
        try {
            mechanicComparator = MechanicComparator.valueOf(comparator);
        } catch (IllegalArgumentException ex) {
            mechanicComparator = MechanicComparator.BY_ALPHABET;
        }
        Comparator<MechanicDto> dtoComparator;
        switch (mechanicComparator) {
            case BY_BUSY:
                dtoComparator = new ByBusy();
                break;
            case BY_ALPHABET:
                dtoComparator = new ByAlphabet();
                break;
            default:
                dtoComparator = new ByAlphabet();
                break;
        }
        return dtoComparator;
    }

    public void mechanicsFromCsv() throws ServiceException {
        service.mechanicsFromCsv();
    }
    public void mechanicsToCsv() {
        service.mechanicsToCsv();
    }
}
