package eu.senla.course.entity.comparator.order;

import eu.senla.course.dto.order.OrderDto;

import java.util.Comparator;

public class ByPlannedDate implements Comparator<OrderDto> {
    @Override
    public int compare(OrderDto o1, OrderDto o2) {
        if (o1 != null && o2 != null) {
            return (o1.getCompleteDate() != null && o1.getPlannedDate() != null) ? o1.getPlannedDate().compareTo(o2.getPlannedDate()) : -1;
        } else if (o1 == null && o2 != null) {
            return 1;
        }
        return -1;
    }
}
