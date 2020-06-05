package eu.senla.course.entity;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Order {
    private int id;
    private LocalDateTime requestDate;
    private LocalDateTime plannedDate;
    private LocalDateTime startDate;
    private LocalDateTime completeDate;
    private Mechanic mechanic;
    private Spot spot;
    private Service[] services;
    private double price;
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

    public Service[] getServices() {
        return services;
    }

    public void setServices(Service[] services) {
        this.services = services;
        if (services != null){
            int hours = 0;
            for (Service service: services){
                if (service!= null) {
                    hours += service.getHours();
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
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
                ", status = " + status +
                '}';
    }
}
