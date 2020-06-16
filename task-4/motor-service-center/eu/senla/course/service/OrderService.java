package eu.senla.course.service;

import eu.senla.course.api.IGarageService;
import eu.senla.course.api.IOrderService;
import eu.senla.course.entity.Mechanic;
import eu.senla.course.entity.Order;
import eu.senla.course.entity.OrderStatus;
import eu.senla.course.entity.Tool;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class OrderService implements IOrderService {
    private List<Order> orders;

    public OrderService() {
        this.orders = new ArrayList<>();
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Order getOrderById(int id){
        if (orders == null || orders.size() == 0 || orders.get(id) == null){
            System.out.println("Order is not found");
            return null;
        }
        return orders.get(id);

    }

    public void addOrder(Order order){
        orders.add(order);
    }

    public void deleteOrder(Order order){
        if (order == null || orders.size() == 0){
            System.out.println("Order is not exist");
        } else {
            orders.removeIf(e -> e.equals(order));
        }
    }
    public void changeStatusOrder(Order order, OrderStatus status){
        if (order == null) {
            System.out.println("Order is not found");
        } else {
            order.setStatus(status);
        }
    }
    public List<Order> ordersForPeriod(Comparator<Order> comparator, OrderStatus status, LocalDateTime startDate, LocalDateTime endDate){
        if (orders == null || orders.size() == 0){
            System.out.println("Orders are not exist");
            return null;
        }
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
        if (orders == null || orders.size() == 0){
            System.out.println("Orders are not exist");
            return null;
        }
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
        if (orders == null || orders.size() == 0){
            System.out.println("Orders are not exist");
        } else {
            for(Order order: orders){
                if (order != null && (order.getStartDate()!=null) && order.getStatus() == OrderStatus.IN_PROGRESS && (order.getCompleteDate()!=null) && date.isAfter(order.getStartDate())){
                    order.setStartDate(order.getStartDate().plusHours(hours));
                    if (order.getCompleteDate() != null) {
                        order.setCompleteDate(order.getCompleteDate().plusHours(hours));
                    }

                }
            }
        }
    }

    public List<Order> listOrders(Comparator<Order> comparator){
        if (orders == null || orders.size() == 0){
            System.out.println("Orders are not exist");
            return null;
        }
        orders.sort(comparator);
        return orders;
    }

    public Order mechanicOrder(Mechanic mechanic){
        if (mechanic == null){
            System.out.println("Mechanic does not exist");
            return null;
        }
        if (orders == null || orders.size() == 0){
            System.out.println("Orders are not exist");
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
        if (orders == null || orders.size() == 0){
            System.out.println("Orders are not exist");

        } else {
            for (Order orderExist : orders) {
                if (orderExist != null && orderExist.equals(order)) {
                    return orderExist.getMechanic();
                }
            }
        }
        return null;
    }
    public LocalDateTime nextAvailableDate(IGarageService garage, LocalDate endDate){
        int days = Period.between(LocalDate.now(), endDate).getDays();
        LocalDateTime nextDate = LocalDateTime.now();
        for (int i=0; i < days; i++) {
            if (garage.numberAvailableSpots(nextDate, orders) > 0) {
                return nextDate;
            } else {
                nextDate = nextDate.plusDays(1);
            }
        }
        System.out.println("Have no free date");
        return null;
    }

    public void bill(Order order){
        if (order == null){
            System.out.println("Order is not exist");
        } else if (order.getTools() == null || order.getTools().size() == 0){
            System.out.println("Order has no services...");
        } else {
            BigDecimal amount = BigDecimal.ZERO;
            for (Tool service : order.getTools()) {
                amount = amount.add(service.getHourlyPrice().multiply(BigDecimal.valueOf(service.getHours())));
            }
            order.setPrice(amount);
            System.out.println("Pay your bill " + order.getPrice() + " for order " + order.getId());
        }
    }
}
