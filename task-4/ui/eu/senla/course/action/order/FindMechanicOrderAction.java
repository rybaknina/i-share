package eu.senla.course.action.order;

import eu.senla.course.action.constant.ActionHelper;
import eu.senla.course.api.IAction;
import eu.senla.course.controller.MechanicController;
import eu.senla.course.controller.OrderController;
import eu.senla.course.entity.Mechanic;
import eu.senla.course.service.MechanicService;
import eu.senla.course.service.OrderService;
import eu.senla.course.util.InputValidator;

import java.util.Scanner;

public class FindMechanicOrderAction implements IAction {
    private OrderController orderController = new OrderController(new OrderService());
    private MechanicController mechanicController = new MechanicController(new MechanicService());
    @Override
    public void execute() {
        try (Scanner scanner = new Scanner(System.in)) {

            Integer id = InputValidator.readInteger(scanner, ActionHelper.IN_INTEGER.getName());
            Mechanic mechanic = mechanicController.gerMechanicById(id);

            System.out.println(orderController.mechanicOrder(mechanic));
        }
    }
}
