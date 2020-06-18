package eu.senla.course.entity.comparator.order;

import eu.senla.course.entity.Order;

import java.math.BigDecimal;
import java.util.Comparator;

public class ByPrice implements Comparator<Order> {
    @Override
    public int compare(Order o1, Order o2) {
        if (o1 != null && o2 != null) {
            return o1.getPrice().compareTo(o2.getPrice());
        } else if (o1 == null && o2 != null){
            return 1;
        }
        return -1;
    }
}
