package eu.senla.course.action.order;

import eu.senla.course.api.IAction;
import eu.senla.course.controller.OrderController;
import eu.senla.course.exception.ServiceException;

import java.io.IOException;

public class ImportOrdersAction implements IAction {
    private OrderController orderController = OrderController.getInstance();
    @Override
    public void execute() throws IOException {
        try {
            orderController.ordersFromCsv();
        } catch (ServiceException e) {
            System.err.println("Error import orders from csv");
        }

    }
}
