package eu.senla.course.action.order;

import eu.senla.course.api.IAction;
import eu.senla.course.controller.OrderController;

import java.io.IOException;

public class ExportOrdersAction implements IAction {
    private OrderController orderController = OrderController.getInstance();
    @Override
    public void execute() throws IOException {
        orderController.ordersToCsv();
    }
}
