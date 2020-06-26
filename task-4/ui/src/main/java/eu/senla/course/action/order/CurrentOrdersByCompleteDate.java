package eu.senla.course.action.order;

import eu.senla.course.api.IAction;
import eu.senla.course.controller.OrderController;
import eu.senla.course.entity.comparator.order.ByCompleteDate;
import eu.senla.course.exception.ServiceException;

public class CurrentOrdersByCompleteDate implements IAction {
    private OrderController controller = OrderController.getInstance();

    @Override
    public void execute() {
        try {
            controller.listCurrentOrders(new ByCompleteDate()).forEach(System.out::println);
        } catch (ServiceException e) {
            System.err.println("Service exception " + e.getMessage());
        }
    }
}
