package eu.senla.course.controller;

import eu.senla.course.entity.*;

import java.time.LocalDateTime;

public class Workshop {
    private static final int MAX_CAPACITY = 4;
    private static final int MAX_NUMBER_OF_SERVICES = 5;
    private static final int LIMIT_ORDERS = 8;

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

    // Добавить заказ
    public void addOrder(Order order){
        if (orders[LIMIT_ORDERS - 1] != null) {
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

    // удалить заказ
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

    public int lengthGarages(){
        return garages.length;
    }

    public int lengthAllSpots(){
        int len = 0;
        for (Garage garage: garages){
            len += garage.getSpots().length;
        }
        return len;
    }
    public Service[] listServices(){
        return services;
    }

    public int lengthServices(){
        return services.length;
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

    public Spot[] spotsOnDate(Garage garage, LocalDateTime date){
        Spot[] spots = new Spot[garage.getSpots().length];
        int j = 0;
        for (Order order: orders){
            if (order != null && (order.getCompleteDate()!= null &&
              (order.getCompleteDate().isAfter(date) && order.getStatus() != OrderStatus.CLOSE) ||
              order.getStatus() == OrderStatus.IN_PROGRESS)){

                spots[j] = order.getSpot();
                j++;
            }
        }
        return spots;
    }
    public Spot[] listAvailableSpots(LocalDateTime date){
        Spot[] freeSpots = new Spot[lengthAllSpots()];
        Spot[] busySpots;
        int j = 0;
        for (Garage garage: garages){
            busySpots = spotsOnDate(garage, date);
            for (Spot spot: garage.getSpots()){
                if (spot == null) {
                    continue;
                }
                int contains = 0;
                for (Spot busySpot: busySpots){
                    if (busySpot != null && busySpot.equals(spot)){
                        contains = 1;
                        break;
                    }
                }
                if (contains == 0){
                    freeSpots[j] = spot;
                    j++;
                }
            }
        }
        return freeSpots;
    }

    public int numberAvailableSpots(LocalDateTime futureDate){
        Spot[] spots = listAvailableSpots(futureDate);
        int number = 0;
        for (Spot spot: spots){
            if (spot != null) {
                number += 1;
            }
        }
        return number;

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
    public void changeStartDateOrders(int hours){
        LocalDateTime date = LocalDateTime.now().plusHours(hours);
        for(Order order: orders){
            if (order != null && (order.getStartDate()!=null) && order.getStatus() != OrderStatus.IN_PROGRESS && (order.getCompleteDate()!=null) && date.isAfter(order.getStartDate())){
                order.setStartDate(order.getStartDate().plusHours(hours));
                if (order.getCompleteDate() != null) {
                    order.setCompleteDate(order.getCompleteDate().plusHours(hours));
                }

            }
        }
    }
}
