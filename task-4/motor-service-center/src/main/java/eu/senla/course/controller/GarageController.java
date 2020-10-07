package eu.senla.course.controller;

import eu.senla.course.api.service.IGarageService;
import eu.senla.course.dto.garage.GarageDto;
import eu.senla.course.dto.order.OrderDto;
import eu.senla.course.dto.spot.SpotDto;
import eu.senla.course.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
final public class GarageController {

    private IGarageService service;
    private static GarageController instance;

    private GarageController() {
    }

    public static GarageController getInstance() {
        if (instance == null) {
            instance = new GarageController();
        }
        return instance;
    }
    @Autowired
    public void setController(GarageController controller) {
        this.instance = controller;
    }

    @Autowired
    @Qualifier("garageService")
    public void setGarageService(IGarageService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String welcome() {
        return "Welcome to Rest Api...";
    }

    @PostMapping("/garage")
    public void addGarage(@RequestBody GarageDto garage) throws ServiceException {
        service.addGarage(garage);
    }

    @PutMapping("/garages")
    public void setGarages(@PathVariable List<GarageDto> garages) {
        service.setGarages(garages);
    }

    @GetMapping(path = "/garages", produces = "application/json")
    public @ResponseBody List<GarageDto> getGarages() {
        return service.getGarages();
    }

    @GetMapping("/garage/{id}")
    public GarageDto getGarageById(@PathVariable int id) {
        return service.getGarageById(id);
    }

    @PutMapping("/garage")
    public void updateGarage(@RequestBody GarageDto garageDto) throws ServiceException {
        service.updateGarage(garageDto);
    }

    @DeleteMapping("/garage/{id}")
    public void deleteGarage(@PathVariable int id) {
        service.deleteGarage(id);
    }

    @GetMapping("/count_spots")
    public int lengthAllSpots() {
        return service.lengthAllSpots();
    }

    public List<SpotDto> listAvailableSpots(LocalDateTime futureDate, List<OrderDto> orderDtoList) throws ServiceException {
        return service.listAvailableSpots(futureDate, orderDtoList);
    }

    public int numberAvailableSpots(LocalDateTime futureDate, List<OrderDto> orderDtoList) throws ServiceException {
        return service.numberAvailableSpots(futureDate, orderDtoList);
    }
    public void garagesFromCsv() throws ServiceException {
        service.garagesFromCsv();
    }
    public void garagesToCsv() {
        service.garagesToCsv();
    }
}
