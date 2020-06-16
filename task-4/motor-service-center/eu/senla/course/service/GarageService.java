package eu.senla.course.service;

import eu.senla.course.api.IGarageService;
import eu.senla.course.entity.Garage;
import eu.senla.course.entity.Order;
import eu.senla.course.entity.OrderStatus;
import eu.senla.course.entity.Spot;
import eu.senla.course.util.GeneratorUtil;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GarageService implements IGarageService {

    private List<Garage> garages;

    public GarageService() {
        this.garages = new ArrayList<>();
    }

    public List<Garage> getGarages() {
        return garages;
    }

    public void setGarages(List<Garage> garages) {
        this.garages = garages;
    }

    public void addGarage(Garage garage){
        garages.add(garage);
        if (garage != null){
            garage.setSpots(createSpots(garage));
        }
    }
    public void deleteGarage(Garage garage){
        if (garage == null){
            System.out.println("Garage not found");
            return;
        }
        garages.removeIf(e -> e.equals(garage));
    }

    public Garage getGarageById(int id){
        if (garages == null || garages.size() == 0 || garages.get(id) == null){
            System.out.println("Garage is not found");
            return null;
        }
        return garages.get(id);
    }

    public List<Spot> createSpots(@NotNull Garage garage){
        int len = GeneratorUtil.generateNumber();
        for (int i = 0; i < len; i++){
            ServiceProvider.getInstance().getSpotService().addSpot(new Spot(i+1, garage));
        }
        return ServiceProvider.getInstance().getSpotService().getSpots();
    }

    public int lengthAllSpots(){
        int len = 0;
        if (garages == null || garages.size() == 0){
            return len;
        }
        for (Garage garage: garages){
            len += garage.getSpots().size();
        }
        return len;
    }

    private List<Spot> spotsOnDate(LocalDateTime date, List<Order> orders){
        List<Spot> spots = new ArrayList<>();
        if (orders != null && orders.size() != 0) {
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
        if (garages == null || garages.size() == 0){
            System.out.println("Garages are not exist");
            return null;
        }
        for (Garage garage: garages){
            List<Spot> busySpots = spotsOnDate(date, orders);

            for (Spot spot: garage.getSpots()){
                if (spot != null) {
                    if (busySpots.size() == 0 || !busySpots.contains(spot)) {
                        freeSpots.add(spot);
                    }
                }
            }
        }
        return freeSpots;
    }

    public int numberAvailableSpots(LocalDateTime futureDate, List<Order> orders){
        if (orders == null || orders.size() == 0){
            System.out.println("Orders are not exist");
            return 0;
        }
        return (int) listAvailableSpots(futureDate, orders).stream().filter(Objects::nonNull).count();
    }



}
