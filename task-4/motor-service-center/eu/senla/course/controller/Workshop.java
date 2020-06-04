package eu.senla.course.controller;

import eu.senla.course.entity.Garage;
import eu.senla.course.entity.Order;
import eu.senla.course.entity.Service;

public class Workshop {
    public static final int MAX_CAPACITY = 4;
    public static final int MAX_NUMBER_OF_SERVICES = 5;
    public static final int LIMIT_ORDERS = 8;

    private Garage[] garages;
    private Service[] services;
    private Order[] orders;

    public Workshop() {
        this.garages = new Garage[MAX_CAPACITY];
        this.services = new Service[MAX_NUMBER_OF_SERVICES];
        this.orders = new Order[LIMIT_ORDERS];
    }
    public void addGarage(Garage garage){
        if (garages[MAX_CAPACITY - 1] != null) {
            System.out.println("Workshop is full");
        }
        else {
            for (int i = 0; i < MAX_CAPACITY; i++) {
                if (garages[i] == null) {
                    garages[i] = garage;
                    break;
                }
            }
        }
    }

    public void deleteGarage(int id){
        for (int i=0; i<MAX_CAPACITY; i++){
            if (garages[i].getId() == id){
                garages[i] = null;
                break;
            }
        }
    }

    public void addService(Service service){
        if (services[MAX_NUMBER_OF_SERVICES - 1] != null) {
            System.out.println("Services is over");
        }
        else {
            for (int i = 0; i < MAX_NUMBER_OF_SERVICES; i++) {
                if (services[i] == null) {
                    services[i] = service;
                    break;
                }
            }
        }
    }

    public void deleteService(int id){
        for (int i=0; i<MAX_NUMBER_OF_SERVICES; i++){
            if (services[i].getId() == id){
                services[i] = null;
                break;
            }
        }
    }

    public void addOrder(Order order){
        if (services[LIMIT_ORDERS - 1] != null) {
            System.out.println("Orders limit is over");
        }
        else {
            for (int i = 0; i < LIMIT_ORDERS; i++) {
                if (orders[i] == null) {
                    orders[i] = order;
                    break;
                }
            }
        }
    }

    public void deleteOrder(int id){
        for (int i=0; i<LIMIT_ORDERS; i++){
            if (orders[i].getId() == id){
                orders[i] = null;
                break;
            }
        }
    }

    public Garage[] listGarages(){
        return garages;
    }

    public Service[] listServices(){
        return services;
    }

    public Order[] listOrders(){
        return orders;
    }

    public Service getServiceByName(String name){
        for (Service service: services){
            if (service.getName() == name){
                return service;
            }
        }
        return null;
    }

}
