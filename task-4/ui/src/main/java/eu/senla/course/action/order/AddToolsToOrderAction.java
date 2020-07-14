package eu.senla.course.action.order;

import eu.senla.course.api.IAction;
import eu.senla.course.controller.OrderController;
import eu.senla.course.controller.ToolController;
import eu.senla.course.entity.Order;
import eu.senla.course.enums.ActionHelper;
import eu.senla.course.exception.ServiceException;
import eu.senla.course.util.InputValidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AddToolsToOrderAction implements IAction {
    OrderController orderController = OrderController.getInstance();
    ToolController toolController = ToolController.getInstance();
    @Override
    public void execute() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        Integer id = InputValidator.readInteger(reader, ActionHelper.IN_INTEGER.getName());
        Order order = orderController.getOrderById(id);
        try {
            orderController.addToolsToOrder(order, toolController.getTools());
        } catch (ServiceException e) {
            System.err.println("Service exception " + e.getMessage());
        }
    }
}
