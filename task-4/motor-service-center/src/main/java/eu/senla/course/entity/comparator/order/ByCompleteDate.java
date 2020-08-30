package eu.senla.course.entity.comparator.order;

import eu.senla.course.entity.Order;

import java.util.Comparator;

public class ByCompleteDate implements Comparator<Order> {
    @Override
    public int compare(Order o1, Order o2) {
        if (o1 != null && o2 != null) {
            return (o1.getCompleteDate() != null && o1.getCompleteDate() != null) ? o1.getCompleteDate().compareTo(o2.getCompleteDate()) : -1;
        } else if (o1 == null && o2 != null) {
            return 1;
        }
        return -1;
    }
}
