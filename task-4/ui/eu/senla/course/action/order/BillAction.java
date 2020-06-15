package eu.senla.course.action.order;

import eu.senla.course.action.constant.ActionHelper;
import eu.senla.course.api.IAction;
import eu.senla.course.controller.OrderController;
import eu.senla.course.entity.Order;
import eu.senla.course.service.ServiceProvider;
import eu.senla.course.util.InputValidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BillAction implements IAction {
    OrderController controller = new OrderController(ServiceProvider.getInstance().getOrderService());
    @Override
    public void execute() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        Integer id = InputValidator.readInteger(reader, ActionHelper.IN_INTEGER.getName());
        Order order = controller.getOrderById(id);

        controller.bill(order);
    }
}
