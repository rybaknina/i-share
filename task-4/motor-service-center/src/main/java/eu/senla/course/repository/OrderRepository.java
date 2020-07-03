package eu.senla.course.repository;

import eu.senla.course.api.IRepository;
import eu.senla.course.entity.Order;
import eu.senla.course.exception.RepositoryException;

import java.util.ArrayList;
import java.util.List;

public class OrderRepository implements IRepository<Order> {
    private List<Order> orders;
    private static final OrderRepository instance = new OrderRepository();

    private OrderRepository(){
        orders = new ArrayList<>();
    }

    public static OrderRepository getInstance(){
        return instance;
    }

    @Override
    public void add(Order order) throws RepositoryException {
        if (order == null){
            throw new RepositoryException("Order is not exist");
        }
        orders.add(order);
    }

    @Override
    public void delete(Order order) throws RepositoryException {
        if (orders.size() == 0 || order == null){
            throw new RepositoryException("Order is not found");
        }
        orders.removeIf(e -> e.equals(order));
    }

    @Override
    public Order getById(int id) {
        for (Order order: orders){
            if (order.getId() == id){
                return order;
            }
        }
        return null;
    }

    @Override
    public List<Order> getAll() {
        return orders;
    }

    public void update(Order order) throws RepositoryException{
        int id = orders.indexOf(order);
        if (id < 0){
            throw new RepositoryException("Order is not found");
        }
        orders.set(id, order);
    }
    public void setAll(List<Order> orders){
        this.orders = orders;
    }
    public void addAll(List<Order> orders){
        this.orders.addAll(orders);
    }
}
