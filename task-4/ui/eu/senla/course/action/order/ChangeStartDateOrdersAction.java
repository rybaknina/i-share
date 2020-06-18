package eu.senla.course.action.order;

import eu.senla.course.api.IAction;
import eu.senla.course.controller.OrderController;
import eu.senla.course.enums.ActionHelper;
import eu.senla.course.util.InputValidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ChangeStartDateOrdersAction implements IAction {
    private OrderController controller = OrderController.getInstance();
    @Override
    public void execute() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        Integer hours = InputValidator.readInteger(reader, ActionHelper.IN_INTEGER.getName());

        controller.changeStartDateOrders(hours);
    }
}
