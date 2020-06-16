package eu.senla.course.action.order;

import eu.senla.course.api.IAction;
import eu.senla.course.controller.OrderController;
import eu.senla.course.entity.comparator.order.ByCompleteDate;

public class CurrentOrdersByCompleteDate implements IAction {
    private OrderController controller = OrderController.getInstance();

    @Override
    public void execute() {
        controller.listCurrentOrders(new ByCompleteDate()).forEach(System.out::println);
    }
}
