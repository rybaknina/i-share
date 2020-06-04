package eu.senla.course.controller;

import eu.senla.course.entity.Order;
import eu.senla.course.entity.Service;

public class BillService {
    private Order order;

    public BillService(Order order) {
        this.order = order;
    }

    public void bill(Service[] services){
        if (order == null) return;
        //Service[] services = order.getServices();
        if (services == null){
            System.out.println("Order was close or cancel...");
        }
        else {
            double amount = 0;
            for (Service service : services) {
                amount += service.getHours()*service.getHourlyPrice();
            }
            System.out.println("Pay your bill: " + Math.round(amount));
        }
    }
}
