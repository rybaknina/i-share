package eu.senla.course.action.order;

import eu.senla.course.api.IAction;
import eu.senla.course.controller.OrderController;

import java.io.IOException;

public class GetAllOrdersAction implements IAction {
    private OrderController controller = OrderController.getInstance();

    @Override
    public void execute() throws IOException {
        controller.getOrders().forEach(System.out::println);
    }
}
