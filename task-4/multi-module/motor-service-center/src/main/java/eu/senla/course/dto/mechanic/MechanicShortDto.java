package eu.senla.course.dto.mechanic;

import eu.senla.course.api.entity.IEntity;
import eu.senla.course.entity.Mechanic;

public class MechanicShortDto implements IEntity {
    private int id;
    private String name;

    public MechanicShortDto() {

    }

    public MechanicShortDto(Mechanic mechanic) {
        this.id = mechanic.getId();
        this.name = mechanic.getName();
    }

    public MechanicShortDto(MechanicDto mechanic) {
        this.id = mechanic.getId();
        this.name = mechanic.getName();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
