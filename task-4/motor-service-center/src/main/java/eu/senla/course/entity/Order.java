package eu.senla.course.entity;

import eu.senla.course.api.entity.IEntity;
import eu.senla.course.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Order implements IEntity {
    private static final long serialVersionUID = 385639052892076759L;

    private static final AtomicInteger count = new AtomicInteger(0);
    private int id;
    private LocalDateTime requestDate;
    private LocalDateTime plannedDate;
    private LocalDateTime startDate;
    private LocalDateTime completeDate;
    private Mechanic mechanic;
    private Spot spot;
    private List<Tool> tools = new ArrayList<>();
    private BigDecimal price;
    private OrderStatus status;

    public Order(LocalDateTime requestDate, LocalDateTime plannedDate, Mechanic mechanic, Spot spot) {
        this.id = count.incrementAndGet();
        this.requestDate = requestDate;
        this.plannedDate = plannedDate;
        this.mechanic = mechanic;
        this.spot = spot;
        this.status = OrderStatus.IN_PROGRESS;
    }

    public Order(int id, LocalDateTime requestDate, LocalDateTime plannedDate, Mechanic mechanic, Spot spot) {
        this.id = id;
        this.requestDate = requestDate;
        this.plannedDate = plannedDate;
        this.mechanic = mechanic;
        this.spot = spot;
        this.status = OrderStatus.IN_PROGRESS;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static AtomicInteger getCount() {
        return count;
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

    public List<Tool> getTools() {
        return tools;
    }

    public void setTools(List<Tool> tools) {
        this.tools = tools;
        int hours = 0;
        if (tools != null) {
            for (int i = 0; i < tools.size(); i++) {
                if (tools.get(i) != null) {
                    hours += tools.get(i).getHours();
                }
            }
            if (startDate != null) {
                this.setCompleteDate(startDate.plusHours(hours));
            }
        }
    }

    public Mechanic getMechanic() {
        return mechanic;
    }

    public void setMechanic(Mechanic mechanic) {
        this.mechanic = mechanic;
    }

    public Spot getSpot() {
        return spot;
    }

    public void setSpot(Spot spot) {
        this.spot = spot;
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
    // закрыть/отменить заказ
    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id = " + id +
                ", requestDate = " + ((requestDate != null) ? requestDate.format(DateTimeFormatter.ofPattern("d.MM.uuuu HH:mm")) : "") +
                ", plannedDate = " + ((plannedDate != null) ? plannedDate.format(DateTimeFormatter.ofPattern("d.MM.uuuu HH:mm")) : "") +
                ", completeDate = " + ((completeDate != null) ? completeDate.format(DateTimeFormatter.ofPattern("d.MM.uuuu HH:mm")) : "") +
                ", price = " + price +
                ", status = " + status +
                '}';
    }
}
