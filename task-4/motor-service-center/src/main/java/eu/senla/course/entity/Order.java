package eu.senla.course.entity;

import eu.senla.course.api.entity.IEntity;
import eu.senla.course.enums.OrderStatus;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "`order`")
public class Order implements IEntity {
    private static final long serialVersionUID = 385639052892076759L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "request_date")
    private LocalDateTime requestDate;
    @Column(name = "planned_date")
    private LocalDateTime plannedDate;
    @Column(name = "start_date")
    private LocalDateTime startDate;
    @Column(name = "complete_date")
    private LocalDateTime completeDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mechanic_id")
    private Mechanic mechanic;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spot_id")
    private Spot spot;
    @OneToMany(mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @Cascade(org.hibernate.annotations.CascadeType.REPLICATE)
    private List<Tool> tools = new ArrayList<>();
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public Order() {

    }

    public Order(LocalDateTime requestDate, LocalDateTime plannedDate, Mechanic mechanic, Spot spot) {
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
