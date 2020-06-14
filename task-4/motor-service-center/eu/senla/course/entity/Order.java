package eu.senla.course.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private int id;
    private LocalDateTime requestDate;
    private LocalDateTime plannedDate;
    private LocalDateTime startDate;
    private LocalDateTime completeDate;
    private Mechanic mechanic;
    private Spot spot;
    private List<Tool> services = new ArrayList<>();
    private BigDecimal price;
    private OrderStatus status;

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

    public LocalDateTime getRequestDate() {
        return requestDate;
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

    public List<Tool> getServices() {
        return services;
    }

    public void setServices(List<Tool> services) {
        this.services = services;
        int hours = 0;
        if (services != null){
            for (int i=0; i< services.size(); i++){
                if (services.get(i) != null){
                    hours += services.get(i).getHours();
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
                ", requestDate = " + ((requestDate!=null)? requestDate.format(DateTimeFormatter.ofPattern("d.MM.uuuu HH:mm")):"") +
                ", plannedDate = " + ((plannedDate!=null)? plannedDate.format(DateTimeFormatter.ofPattern("d.MM.uuuu HH:mm")):"") +
                ", completeDate = " + ((completeDate!=null)? completeDate.format(DateTimeFormatter.ofPattern("d.MM.uuuu HH:mm")):"") +
                ", price = " + price +
                ", status = " + status +
                '}';
    }
}
