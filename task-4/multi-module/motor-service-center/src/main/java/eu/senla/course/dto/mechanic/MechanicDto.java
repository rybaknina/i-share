package eu.senla.course.dto.mechanic;

import eu.senla.course.api.entity.IEntity;
import eu.senla.course.dto.garage.GarageDto;
import eu.senla.course.dto.order.OrderShortDto;
import eu.senla.course.entity.Mechanic;
import eu.senla.course.entity.Order;
import eu.senla.course.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MechanicDto implements IEntity {
    private int id;
    private String name;
    private List<OrderShortDto> orderShortDtoList = new ArrayList<>();
    private GarageDto garageDto;

    public MechanicDto() {

    }

    public MechanicDto(Mechanic mechanic) {
        this.id = mechanic.getId();
        this.name = mechanic.getName();
        for (Order order: mechanic.getOrders()) {
            this.orderShortDtoList.add(new OrderShortDto(order));
        }
        if (mechanic.getGarage() != null) {
            this.garageDto = new GarageDto(mechanic.getGarage());
        }
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

    public List<OrderShortDto> getOrderShortDtoList() {
        return orderShortDtoList;
    }

    public void setOrderShortDtoList(List<OrderShortDto> orderShortDtoList) {
        this.orderShortDtoList = orderShortDtoList;
    }

    public GarageDto getGarageShortDto() {
        return garageDto;
    }

    public void setGarageShortDto(GarageDto garageDto) {
        this.garageDto = garageDto;
    }
    // если у механика есть заказ, у которого дата завершения меньше (до) текущего времени или в статусе выполнения, то - не свободен
    public boolean isMechanicFree() {
        if (orderShortDtoList == null || orderShortDtoList.size() == 0) {
            return true;
        }
        for (OrderShortDto order: orderShortDtoList) {
            if (order != null) {
                if (order.getCompleteDate() != null && order.getCompleteDate().isBefore(LocalDateTime.now())) {
                    return false;
                }
                if (order.getStatus() == OrderStatus.IN_PROGRESS) {
                    return false;
                }
            }
        }
        return true;
    }
}
