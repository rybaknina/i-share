package eu.senla.course.action.order;

import eu.senla.course.action.constant.ActionHelper;
import eu.senla.course.api.IAction;
import eu.senla.course.controller.OrderController;
import eu.senla.course.service.OrderService;
import eu.senla.course.util.InputValidator;

import java.util.Scanner;

public class ChangeStartDateOrdersAction implements IAction {
    private OrderController controller = new OrderController(new OrderService());
    @Override
    public void execute() {
        try (Scanner scanner = new Scanner(System.in)) {

            Integer hours = InputValidator.readInteger(scanner, ActionHelper.IN_INTEGER.getName());

            controller.changeStartDateOrders(hours);
        }
    }
}
