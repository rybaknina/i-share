package eu.senla.course.dto.spot;

import eu.senla.course.api.entity.IEntity;
import eu.senla.course.dto.garage.GarageDto;
import eu.senla.course.entity.Spot;

public class SpotDto implements IEntity {

    private int id;
    private GarageDto garageDto;

    public SpotDto() {

    }

    public SpotDto(Spot spot) {
        this.id = spot.getId();
        if (spot.getGarage() != null) {
            this.garageDto = new GarageDto(spot.getGarage());
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public GarageDto getGarageDto() {
        return garageDto;
    }

    public void setGarageDto(GarageDto garageDto) {
        this.garageDto = garageDto;
    }
}
