package eu.senla.course.controller;

import eu.senla.course.entity.Order;
import eu.senla.course.entity.Service;

public class BillService {

    public void bill(Order order){
        if (order == null){
            System.out.println("Order is not exist");
            return;
        }
        if (order.getServices() == null){
            System.out.println("Order has no services...");
        }
        else {
            double amount = 0;
            for (Service service : order.getServices()) {
                amount += service.getHours()*service.getHourlyPrice();
            }
            order.setPrice(Math.round(amount));
            System.out.println("Pay your bill: " + order.getPrice());
        }
    }
}
