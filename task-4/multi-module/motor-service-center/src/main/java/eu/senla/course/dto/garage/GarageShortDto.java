package eu.senla.course.dto.garage;

import eu.senla.course.api.entity.IEntity;
import eu.senla.course.entity.Garage;

public class GarageShortDto implements IEntity {
    private int id;
    private String name;

    public GarageShortDto() {

    }

    public GarageShortDto(Garage garage) {
        this.id = garage.getId();
        this.name = garage.getName();
    }

    public GarageShortDto(GarageDto garage) {
        this.id = garage.getId();
        this.name = garage.getName();
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
}
