package eu.senla.course.action.order;

import eu.senla.course.action.constant.ActionHelper;
import eu.senla.course.api.IAction;
import eu.senla.course.controller.OrderController;
import eu.senla.course.entity.Order;
import eu.senla.course.service.OrderService;
import eu.senla.course.util.InputValidator;

import java.util.Scanner;

public class FindOrderMechanicAction implements IAction {
    private OrderController controller = new OrderController(new OrderService());
    @Override
    public void execute() {
        try (Scanner scanner = new Scanner(System.in)) {

            Integer id = InputValidator.readInteger(scanner, ActionHelper.IN_INTEGER.getName());
            Order order = controller.getOrderById(id);

            System.out.println(controller.orderMechanic(order));
        }
    }
}
