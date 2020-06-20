package eu.senla.course.action.order;

import eu.senla.course.api.IAction;
import eu.senla.course.controller.MechanicController;
import eu.senla.course.controller.OrderController;
import eu.senla.course.entity.Mechanic;
import eu.senla.course.enums.ActionHelper;
import eu.senla.course.util.InputValidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FindMechanicOrderAction implements IAction {
    private OrderController orderController = OrderController.getInstance();
    private MechanicController mechanicController = MechanicController.getInstance();
    @Override
    public void execute() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        Integer id = InputValidator.readInteger(reader, ActionHelper.IN_INTEGER.getName()) - 1;
        Mechanic mechanic = mechanicController.gerMechanicById(id);

        System.out.println(orderController.mechanicOrder(mechanic));
    }
}
