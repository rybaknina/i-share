package eu.senla.course.action.order;

import eu.senla.course.api.IAction;
import eu.senla.course.controller.OrderController;
import eu.senla.course.entity.comparator.order.ByPrice;

public class CurrentOrdersByPrice implements IAction {
    private OrderController controller = OrderController.getInstance();

    @Override
    public void execute() {
        controller.listCurrentOrders(new ByPrice()).forEach(System.out::println);
    }
}
