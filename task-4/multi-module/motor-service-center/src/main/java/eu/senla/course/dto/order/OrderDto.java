package eu.senla.course.dto.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import eu.senla.course.api.entity.IEntity;
import eu.senla.course.dto.mechanic.MechanicShortDto;
import eu.senla.course.dto.spot.SpotShortDto;
import eu.senla.course.dto.tool.ToolShortDto;
import eu.senla.course.entity.Order;
import eu.senla.course.entity.Tool;
import eu.senla.course.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderDto implements IEntity {

    private int id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime requestDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime plannedDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime completeDate;
    private MechanicShortDto mechanicShortDto;
    private SpotShortDto spotShortDto;
    private List<ToolShortDto> toolShortDtoList = new ArrayList<>();
    private BigDecimal price;
    private OrderStatus status;

    public OrderDto() {

    }

    public OrderDto(Order order) {
        this.id = order.getId();
        this.requestDate = order.getRequestDate();
        this.plannedDate = order.getPlannedDate();
        this.startDate = order.getStartDate();
        this.completeDate = order.getCompleteDate();
        if (order.getMechanic() != null) {
            this.mechanicShortDto = new MechanicShortDto(order.getMechanic());
        }
        if (order.getSpot() != null) {
            this.spotShortDto = new SpotShortDto(order.getSpot());
        }
        if (order.getTools() != null) {
            for (Tool tool : order.getTools()) {
                this.toolShortDtoList.add(new ToolShortDto(tool));
            }
        }
        this.price = order.getPrice();
        this.status = order.getStatus();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDateTime requestDate) {
        this.requestDate = requestDate;
    }

    public LocalDateTime getPlannedDate() {
        return plannedDate;
    }

    public void setPlannedDate(LocalDateTime plannedDate) {
        this.plannedDate = plannedDate;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(LocalDateTime completeDate) {
        this.completeDate = completeDate;
    }

    public MechanicShortDto getMechanicShortDto() {
        return mechanicShortDto;
    }

    public void setMechanicShortDto(MechanicShortDto mechanicShortDto) {
        this.mechanicShortDto = mechanicShortDto;
    }

    public SpotShortDto getSpotShortDto() {
        return spotShortDto;
    }

    public void setSpotShortDto(SpotShortDto spotShortDto) {
        this.spotShortDto = spotShortDto;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public List<ToolShortDto> getToolShortDtoList() {
        return toolShortDtoList;
    }

    public void setToolShortDtoList(List<ToolShortDto> toolShortDtoList) {
        this.toolShortDtoList = toolShortDtoList;
    }
}
