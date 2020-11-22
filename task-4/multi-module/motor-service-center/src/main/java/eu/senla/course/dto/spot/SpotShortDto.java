package eu.senla.course.dto.spot;

import eu.senla.course.api.entity.IEntity;
import eu.senla.course.entity.Spot;

public class SpotShortDto implements IEntity {

    private int id;

    public SpotShortDto() {

    }

    public SpotShortDto(Spot spot) {
        this.id = spot.getId();
    }

    public SpotShortDto(SpotDto spot) {
        this.id = spot.getId();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
