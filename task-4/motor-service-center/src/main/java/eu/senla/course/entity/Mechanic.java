package eu.senla.course.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Mechanic {
    private static final AtomicInteger count = new AtomicInteger(0);
    private int id;
    private String name;
    private List<Order> orders = new ArrayList<>();
    private Garage garage;

    public Mechanic(String name) {
        this.id = count.incrementAndGet();
        this.name = name;
    }

    public Mechanic(String name, Garage garage) {
        this(name);
        this.garage = garage;
    }

    public int getId() {
        return id;
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
    public boolean isMechanicFree(){
        if (orders == null || orders.size() == 0){
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
