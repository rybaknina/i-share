package eu.senla.course.view;

import eu.senla.course.api.IWorkshop;
import eu.senla.course.controller.*;
import eu.senla.course.entity.*;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

public class Workshop implements IWorkshop {

    private final static Workshop instance = new Workshop();
    private final GarageManager garageManager;
    private final SpotManager spotManager;
    private final MechanicManager mechanicManager;
    private final ServiceManager serviceManager;
    private final OrderManager orderManager;
    private final BillService billService;


    private Workshop() {
        ManagerProvider provider = ManagerProvider.getInstance();
        garageManager = provider.getGarageManager();
        spotManager = provider.getSpotManager();
        mechanicManager = provider.getMechanicManager();
        serviceManager = provider.getServiceManager();
        orderManager = provider.getOrderManager();
        billService = provider.getBillService();
    }

    public static Workshop getInstance(){
        return instance;
    }

    @Override
    public void addGarage(Garage garage) {
        garageManager.addGarage(garage);
    }

    @Override
    public Garage getGarageById(int id) {
        return garageManager.getGarageById(id);
    }

    @Override
    public int lengthGarages() {
        return garageManager.lengthGarages();
    }

    @Override
    public List<Garage> getGarages() {
        return garageManager.getGarages();
    }

    @Override
    public void setGarages(List<Garage> garages) {
        garageManager.setGarages(garages);
    }

    @Override
    public void deleteGarage(Garage garage) {
        garageManager.deleteGarage(garage);
    }

    @Override
    public int lengthAllSpots() {
        return garageManager.lengthAllSpots();
    }

    @Override
    public void addSpot(Spot spot) {
        spotManager.addSpot(spot);
    }

    @Override
    public void addMechanic(Mechanic mechanic) {
        mechanicManager.addMechanic(mechanic);
    }

    @Override
    public List<Mechanic> getMechanics() {
        return mechanicManager.getMechanics();
    }

    @Override
    public void setMechanics(List<Mechanic> mechanics) {
        mechanicManager.setMechanics(mechanics);
    }

    @Override
    public int lengthMechanics() {
        return mechanicManager.lengthMechanics();
    }

    @Override
    public void deleteMechanic(Mechanic mechanic) {
        mechanicManager.deleteMechanic(mechanic);
    }

    @Override
    public Mechanic gerMechanicById(int id) {
        return mechanicManager.gerMechanicById(id);
    }

    @Override
    public Mechanic firstFreeMechanic() {
        return mechanicManager.firstFreeMechanic();
    }

    @Override
    public void sortMechanicsBy(Comparator<Mechanic> comparator) {
        mechanicManager.sortMechanicsBy(comparator);
    }

    @Override
    public void addService(Service service) {
        serviceManager.addService(service);
    }

    @Override
    public List<Service> getServices() {
        return serviceManager.getServices();
    }

    @Override
    public void setServices(List<Service> services) {
        serviceManager.setServices(services);
    }

    @Override
    public int lengthServices() {
        return serviceManager.lengthServices();
    }

    @Override
    public void addOrder(Order order) {
        orderManager.addOrder(order);
    }

    @Override
    public void bill(Order order) {
        billService.bill(order);
    }

    @Override
    public List<Order> getOrders() {
        return orderManager.getOrders();
    }

    @Override
    public List<Spot> listAvailableSpots(LocalDateTime date, List<Order> orders) {
        return garageManager.listAvailableSpots(date, orders);
    }

    @Override
    public int numberAvailableSpots(LocalDateTime futureDate, List<Order> orders) {
        return garageManager.numberAvailableSpots(futureDate, orders);
    }

    @Override
    public List<Order> listOrders(Comparator<Order> comparator) {
        return orderManager.listOrders(comparator);
    }

    @Override
    public void changeStartDateOrders(int hours) {
        orderManager.changeStartDateOrders(hours);
    }

    @Override
    public LocalDateTime nextAvailableDate(List<Order> orders) {
        return garageManager.nextAvailableDate(orders);
    }

    @Override
    public Order mechanicOrder(Mechanic mechanic) {
        return orderManager.mechanicOrder(mechanic);
    }

    @Override
    public Mechanic orderMechanic(Order order) {
        return orderManager.orderMechanic(order);
    }

    @Override
    public List<Order> ordersForPeriod(Comparator<Order> comparator, OrderStatus status, LocalDateTime startDate, LocalDateTime endDate) {
        return orderManager.ordersForPeriod(comparator, status, startDate, endDate);
    }

    @Override
    public List<Order> listCurrentOrders(Comparator<Order> comparator) {
        return orderManager.listCurrentOrders(comparator);
    }
}
