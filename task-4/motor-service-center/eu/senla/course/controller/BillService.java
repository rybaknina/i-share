package eu.senla.course.controller;

import eu.senla.course.entity.Order;
import eu.senla.course.entity.Service;

import java.math.BigDecimal;


public class BillService {

    public void bill(Order order){
        if (order == null){
            System.out.println("Order is not exist");
        } else if (order.getServices() == null){
            System.out.println("Order has no services...");
        } else {
            BigDecimal amount = BigDecimal.ZERO;
            for (Service service : order.getServices()) {
                amount = amount.add(service.getHourlyPrice().multiply(BigDecimal.valueOf(service.getHours())));
            }
            order.setPrice(amount);
            System.out.println("Pay your bill " + order.getPrice() + " for order " + order.getId());
        }
    }
}
