package eu.senla.course.controller;

import eu.senla.course.entity.Garage;
import eu.senla.course.entity.Order;
import eu.senla.course.entity.OrderStatus;
import eu.senla.course.entity.Spot;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class GarageManager {
    private static final int MAX_CAPACITY = 4;
    private List<Garage> garages;

    public GarageManager() {
        this.garages = new ArrayList<>();
    }

    public List<Garage> getGarages() {
        return garages;
    }

    public void setGarages(List<Garage> garages) {
        this.garages = garages;
    }

    public void addGarage(Garage garage){
        if (garages.size() == MAX_CAPACITY) {
            System.out.println("Workshop is full");
        }
        else {
            garages.add(garage);
        }
    }
    public void deleteGarage(Garage garage){
        garages.removeIf(e -> e.equals(garage));
    }

    public Garage getGarageById(int id){
        return (garages == null)? null: garages.get(id);
    }
    public int lengthGarages(){
        return MAX_CAPACITY;
    }
    public int lengthAllSpots(){
        int len = 0;
        for (Garage garage: garages){
            len += garage.getSpots().size();
        }
        return len;
    }

    private List<Spot> spotsOnDate(LocalDateTime date, List<Order> orders){
        List<Spot> spots = new ArrayList<>();
        if (orders != null) {
            for (Order order : orders) {
                if (order != null && order.getSpot() != null && (order.getCompleteDate() != null &&
                        (order.getCompleteDate().isAfter(date) && order.getStatus() != OrderStatus.CLOSE) ||
                        order.getStatus() == OrderStatus.IN_PROGRESS)) {
                    spots.add(order.getSpot());
                }
            }
        }
        return spots;
    }

    public List<Spot> listAvailableSpots(LocalDateTime date, List<Order> orders){
        List<Spot> freeSpots = new ArrayList<>();

        for (Garage garage: garages){
            List<Spot> busySpots = spotsOnDate(date, orders);

            for (Spot spot: garage.getSpots()){
                if (spot != null) {
                    int contains = 0;
                    if (busySpots.size()!= 0 ) {
                        for (Spot busySpot : busySpots) {
                            if (busySpot != null && busySpot.equals(spot)) {
                                contains = 1;
                                break;
                            }
                        }
                    }
                    if (contains == 0) {
                        freeSpots.add(spot);
                    }
                }
            }
        }
        return freeSpots;
    }

    public int numberAvailableSpots(LocalDateTime futureDate, List<Order> orders){
        return (int) listAvailableSpots(futureDate, orders).stream().filter(Objects::nonNull).count();
    }

    public LocalDateTime nextAvailableDate(List<Order> orders){
        int days = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
        LocalDateTime nextDate = LocalDateTime.now();
        for (int i=0; i < days; i++) {
            if (numberAvailableSpots(nextDate, orders) > 0) {
                return nextDate;
            } else {
                nextDate = nextDate.plusDays(1);
            }
        }
        return null;
    }

}
