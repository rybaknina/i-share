package eu.senla.course.entity;

import java.time.LocalDateTime;

public class Mechanic {
    private int id;
    private String name;
    private Order[] orders;
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

    public Order[] getOrders() {
        return orders;
    }

    public void setOrders(Order[] orders) {
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
        if (orders == null){
            return true;
        }
        for (Order order: orders){
            if (order == null) {
                continue;
            }
            if (order.getCompleteDate()!= null && order.getCompleteDate().isBefore(LocalDateTime.now())) { return false;}
            if (order.getStatus()==OrderStatus.IN_PROGRESS) {return false;}
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
