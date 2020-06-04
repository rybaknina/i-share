package eu.senla.course.entity;

import java.time.LocalDateTime;

public class Order {
    private int id;
    private LocalDateTime requestDate;
    private LocalDateTime plannedDate;
    private LocalDateTime startDate;
    private LocalDateTime completeDate;
    private Mechanic mechanic;
    private Garage garage;
    private Spot spot;
    private Service[] services;
    private double price;
    private OrderStatus status;

    public Order(int id, LocalDateTime plannedDate, Mechanic mechanic, Garage garage, Spot spot) {
        this.id = id;
        this.requestDate = LocalDateTime.now();
        this.plannedDate = plannedDate;
        this.mechanic = mechanic;
        this.garage = garage;
        this.spot = spot;
        this.status = OrderStatus.IN_PROGRESS;
    }

    public int getId() {
        return id;
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
