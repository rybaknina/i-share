package eu.senla.course.entity;

import eu.senla.course.api.entity.IEntity;
import eu.senla.course.enums.OrderStatus;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "mechanic")
public class Mechanic implements IEntity {
    private static final long serialVersionUID = -625749130457859021L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @OneToMany(mappedBy = "mechanic", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @Cascade(org.hibernate.annotations.CascadeType.REPLICATE)
    private List<Order> orders = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "garage_id")
    private Garage garage;

    public Mechanic() {

    }

    public Mechanic(String name) {
        this.name = name;
    }

    public Mechanic(String name, Garage garage) {
        this(name);
        this.garage = garage;
    }

    public Mechanic(int id, String name, Garage garage) {
        this.id = id;
        this.name = name;
        this.garage = garage;
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

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Garage getGarage() {
        return garage;
    }

    public void setGarage(Garage garage) {
        this.garage = garage;
    }

    // если у механика есть заказ, у которого дата завершения меньше (до) текущего времени или в статусе выполнения, то - не свободен
    public boolean isMechanicFree() {
        if (orders == null || orders.size() == 0) {
            return true;
        }
        for (Order order: orders) {
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

    @Override
    public String toString() {
        return "Mechanic{" +
                "id = " + id +
                ", name = '" + name + '\'' +
                ", garage = " + garage +
                '}';
    }
}
