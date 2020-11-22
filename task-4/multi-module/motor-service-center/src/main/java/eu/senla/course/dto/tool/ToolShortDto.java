package eu.senla.course.dto.tool;

import eu.senla.course.api.entity.IEntity;
import eu.senla.course.entity.Tool;

import java.math.BigDecimal;

public class ToolShortDto implements IEntity {
    private int id;
    private String name;
    private int hours;
    private BigDecimal hourlyPrice;

    public ToolShortDto() {

    }

    public ToolShortDto(Tool tool) {
        this.id = tool.getId();
        this.name = tool.getName();
        this.hours = tool.getHours();
        this.hourlyPrice = tool.getHourlyPrice();
    }

    public ToolShortDto(ToolDto tool) {
        this.id = tool.getId();
        this.name = tool.getName();
        this.hours = tool.getHours();
        this.hourlyPrice = tool.getHourlyPrice();
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

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public BigDecimal getHourlyPrice() {
        return hourlyPrice;
    }

    public void setHourlyPrice(BigDecimal hourlyPrice) {
        this.hourlyPrice = hourlyPrice;
    }
}
