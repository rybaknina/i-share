package eu.senla.course.controller;

import eu.senla.course.entity.Mechanic;
import eu.senla.course.entity.Order;
import eu.senla.course.entity.OrderStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class OrderManager {
    private static final int LIMIT_ORDERS = 8;
    private List<Order> orders;

    public OrderManager() {
        this.orders = new ArrayList<>();
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Order getOrderById(int id){
        return (orders == null)? null: orders.get(id);
    }

    public void addOrder(Order order){
        if (orders.size() == LIMIT_ORDERS) {
            System.out.println("Orders limit is over");
        }
        else {
            orders.add(order);
        }
    }

    public void deleteOrder(Order order){
        orders.removeIf(e -> e.equals(order));
    }

    public List<Order> ordersForPeriod(Comparator<Order> comparator, OrderStatus status, LocalDateTime startDate, LocalDateTime endDate){
        List<Order> ordersForPeriod = new ArrayList<>();
        for (Order order: orders){
            if (order!= null && order.getStartDate()!=null && order.getStatus() == status && order.getStartDate().compareTo(startDate) >= 0 && order.getStartDate().compareTo(endDate) <= 0 ){
                ordersForPeriod.add(order);
            }
        }
        ordersForPeriod.sort(comparator);
        return ordersForPeriod;
    }
    public List<Order> listCurrentOrders(Comparator<Order> comparator){
        List<Order> currentOrders = new ArrayList<>();
        for (Order order: orders){
            if (order != null && order.getStatus() == OrderStatus.IN_PROGRESS){
                currentOrders.add(order);
            }
        }
        currentOrders.sort(comparator);
        return currentOrders;
    }
    public void changeStartDateOrders(int hours){
        LocalDateTime date = LocalDateTime.now().plusHours(hours);
        for(Order order: orders){
            if (order != null && (order.getStartDate()!=null) && order.getStatus() == OrderStatus.IN_PROGRESS && (order.getCompleteDate()!=null) && date.isAfter(order.getStartDate())){
                order.setStartDate(order.getStartDate().plusHours(hours));
                if (order.getCompleteDate() != null) {
                    order.setCompleteDate(order.getCompleteDate().plusHours(hours));
                }

            }
        }
    }

    public List<Order> listOrders(Comparator<Order> comparator){
        orders.sort(comparator);
        return orders;
    }

    public Order mechanicOrder(Mechanic mechanic){
        if (mechanic == null){
            System.out.println("Mechanic does not exist");
            return null;
        }
        for (Order order: orders){
            if (order.getStatus() == OrderStatus.IN_PROGRESS){
                return order;
            }
        }
        return null;
    }

    public Mechanic orderMechanic(Order order){
        for (Order orderExist: orders){
            if (orderExist!= null && orderExist.equals(order)){
                return orderExist.getMechanic();
            }
        }
        return null;
    }
}
