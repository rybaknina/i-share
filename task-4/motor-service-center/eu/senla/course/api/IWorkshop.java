package eu.senla.course.api;

import eu.senla.course.entity.*;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

public interface IWorkshop {
    void addGarage(Garage garage);
    int lengthGarages();
    void setGarages(List<Garage> garages);
    List<Garage> getGarages();
    Garage getGarageById(int id);
    void deleteGarage(Garage garage);
    int lengthAllSpots();
    void addSpot(Spot spot);
    List<Spot> listAvailableSpots(LocalDateTime date, List<Order> orders);
    int numberAvailableSpots(LocalDateTime futureDate, List<Order> orders);
    void addMechanic(Mechanic mechanic);
    List<Mechanic> getMechanics();
    int lengthMechanics();
    void setMechanics(List<Mechanic> mechanics);
    void deleteMechanic(Mechanic mechanic);
    Mechanic gerMechanicById(int id);
    Mechanic firstFreeMechanic();
    void sortMechanicsBy(Comparator<Mechanic> comparator);
    void addService(Service service);
    List<Service> getServices();
    void setServices(List<Service> services);
    int lengthServices();
    void addOrder(Order order);
    List<Order> getOrders();
    List<Order> listOrders(Comparator<Order> comparator);
    void changeStartDateOrders(int hours);
    LocalDateTime nextAvailableDate(List<Order> orders);
    Order mechanicOrder(Mechanic mechanic);
    Mechanic orderMechanic(Order order);
    List<Order> ordersForPeriod(Comparator<Order> comparator, OrderStatus status, LocalDateTime startDate, LocalDateTime endDate);
    List<Order> listCurrentOrders(Comparator<Order> comparator);
    void bill(Order order);
    // TODO: add all functional from managers


}
