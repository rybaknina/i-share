package eu.senla.course.action.order;

import eu.senla.course.api.IAction;
import eu.senla.course.controller.OrderController;
import eu.senla.course.entity.comparator.order.ByCompleteDate;
import eu.senla.course.service.ServiceProvider;

public class CurrentOrdersByCompleteDate implements IAction {
    private OrderController controller = new OrderController(ServiceProvider.getInstance().getOrderService());

    @Override
    public void execute() {
        controller.listCurrentOrders(new ByCompleteDate()).forEach(System.out::println);
    }
}
