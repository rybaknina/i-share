package eu.senla.course.dto.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import eu.senla.course.api.entity.IEntity;
import eu.senla.course.entity.Order;
import eu.senla.course.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderShortDto implements IEntity {

    private int id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime requestDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime plannedDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime completeDate;
    private BigDecimal price;
    private OrderStatus status;

    public OrderShortDto() {

    }

    public OrderShortDto(Order order) {
        this.id = order.getId();
        this.requestDate = order.getRequestDate();
        this.plannedDate = order.getPlannedDate();
        this.startDate = order.getStartDate();
        this.completeDate = order.getCompleteDate();
        this.price = order.getPrice();
        this.status = order.getStatus();
    }

    public OrderShortDto(OrderDto order) {
        this.id = order.getId();
        this.requestDate = order.getRequestDate();
        this.plannedDate = order.getPlannedDate();
        this.startDate = order.getStartDate();
        this.completeDate = order.getCompleteDate();
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
}
