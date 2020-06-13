package eu.senla.course.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Mechanic {
    private int id;
    private String name;
    private List<Order> orders = new ArrayList<>();
    private Garage garage;

    public Mechanic(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public int getId() {
        return id;
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
                '}';
    }
}
