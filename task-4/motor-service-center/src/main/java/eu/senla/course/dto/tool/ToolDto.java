package eu.senla.course.dto.tool;

import eu.senla.course.api.entity.IEntity;
import eu.senla.course.dto.order.OrderDto;
import eu.senla.course.entity.Tool;

import java.math.BigDecimal;

public class ToolDto implements IEntity {
    private int id;
    private String name;
    private int hours;
    private BigDecimal hourlyPrice;
    private OrderDto orderDto;

    public ToolDto() {

    }

    public ToolDto(Tool tool) {
        this.id = tool.getId();
        this.name = tool.getName();
        this.hours = tool.getHours();
        this.hourlyPrice = tool.getHourlyPrice();
        if (tool.getOrder() != null) {
            this.orderDto = new OrderDto(tool.getOrder());
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

    public OrderDto getOrderDto() {
        return orderDto;
    }

    public void setOrderDto(OrderDto orderDto) {
        this.orderDto = orderDto;
    }
}
