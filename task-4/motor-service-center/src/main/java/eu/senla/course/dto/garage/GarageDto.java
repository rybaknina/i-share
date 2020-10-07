package eu.senla.course.dto.garage;

import eu.senla.course.api.entity.IEntity;
import eu.senla.course.dto.mechanic.MechanicShortDto;
import eu.senla.course.dto.spot.SpotShortDto;
import eu.senla.course.entity.Garage;
import eu.senla.course.entity.Mechanic;
import eu.senla.course.entity.Spot;

import java.util.ArrayList;
import java.util.List;

public class GarageDto implements IEntity {
    private int id;
    private String name;
    private List<SpotShortDto> spotShortDtoList = new ArrayList<>();
    private List<MechanicShortDto> mechanicShortDtoList = new ArrayList<>();

    public GarageDto() {

    }

    public GarageDto(Garage garage) {
        this.id = garage.getId();
        this.name = garage.getName();
        if (garage.getSpots() != null) {
            for (Spot spot : garage.getSpots()) {
                SpotShortDto spotShortDto = new SpotShortDto(spot);
                spotShortDtoList.add(spotShortDto);
            }
        }
        if (garage.getMechanics() != null) {
            for (Mechanic mechanic : garage.getMechanics()) {
                MechanicShortDto mechanicShortDto = new MechanicShortDto(mechanic);
                mechanicShortDtoList.add(mechanicShortDto);
            }
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SpotShortDto> getSpotShortDtoList() {
        return spotShortDtoList;
    }

    public void setSpotShortDtoList(List<SpotShortDto> spotShortDtoList) {
        this.spotShortDtoList = spotShortDtoList;
    }

    public List<MechanicShortDto> getMechanicShortDtoList() {
        return mechanicShortDtoList;
    }

    public void setMechanicShortDtoList(List<MechanicShortDto> mechanicShortDtoList) {
        this.mechanicShortDtoList = mechanicShortDtoList;
    }
}
