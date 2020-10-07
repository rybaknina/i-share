package eu.senla.course.controller;

import eu.senla.course.api.service.ISpotService;
import eu.senla.course.dto.garage.GarageDto;
import eu.senla.course.dto.spot.SpotDto;
import eu.senla.course.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
final public class SpotController {

    private ISpotService service;
    private static SpotController instance;

    private SpotController() {
    }

    public static SpotController getInstance() {
        if (instance == null) {
            instance = new SpotController();
        }
        return instance;
    }

    @Autowired
    public void setController(SpotController controller) {
        this.instance = controller;
    }

    @Autowired
    @Qualifier("spotService")
    public void setService(ISpotService service) {
        this.service = service;
    }

    @GetMapping(path = "/spots", produces = "application/json")
    public List<SpotDto> getSpots() {
        return service.getSpots();
    }

    @PutMapping("/spots")
    public void setSpots(@PathVariable List<SpotDto> spotDtoList) {
        service.setSpots(spotDtoList);
    }

    @PostMapping("/spot")
    public void addSpot(@RequestBody SpotDto spotDto) throws ServiceException {
        service.addSpot(spotDto);
    }

    public boolean isModifySpot() {
        return service.isModifySpot();
    }

    @GetMapping("/spot/{id}")
    public SpotDto getSpotById(@PathVariable int id) {
        return service.getSpotById(id);
    }

    @DeleteMapping("/spot/{id}")
    public void deleteSpot(@PathVariable int id) {
        service.deleteSpot(id);
    }

    @PutMapping("/spot")
    public void updateSpot(@RequestBody SpotDto spotDto) throws ServiceException {
        service.updateSpot(spotDto);
    }

    @GetMapping(path = "/spots_in_garage", produces = "application/json")
    public List<SpotDto> spotsInGarage(@PathVariable GarageDto garageDto) {
        return service.spotsInGarage(garageDto);
    }

    public void spotsFromCsv() throws ServiceException {
        service.spotsFromCsv();
    }
    public void spotsToCsv() {
        service.spotsToCsv();
    }
}
