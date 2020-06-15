package eu.senla.course.action.order;

import eu.senla.course.action.constant.ActionHelper;
import eu.senla.course.api.IAction;
import eu.senla.course.controller.MechanicController;
import eu.senla.course.controller.OrderController;
import eu.senla.course.controller.SpotController;
import eu.senla.course.entity.Mechanic;
import eu.senla.course.entity.Order;
import eu.senla.course.entity.Spot;
import eu.senla.course.service.MechanicService;
import eu.senla.course.service.OrderService;
import eu.senla.course.service.SpotService;
import eu.senla.course.util.InputValidator;

import java.time.LocalDateTime;
import java.util.Scanner;

public class AddOrderAction implements IAction {
    private OrderController orderController = new OrderController(new OrderService());
    private MechanicController mechanicController = new MechanicController(new MechanicService());
    private SpotController spotController = new SpotController(new SpotService());
    @Override
    public void execute() {
        try (Scanner scanner = new Scanner(System.in)) {

            Integer id = InputValidator.readInteger(scanner, ActionHelper.IN_INTEGER.getName());
            LocalDateTime requestDate = InputValidator.readDateTime(scanner, ActionHelper.IN_LOCAL_DATE_TIME.getName());
            LocalDateTime plannedDate = InputValidator.readDateTime(scanner, ActionHelper.IN_LOCAL_DATE_TIME.getName());
            Integer mechanicId = InputValidator.readInteger(scanner, ActionHelper.IN_INTEGER.getName());
            Integer spotId = InputValidator.readInteger(scanner, ActionHelper.IN_INTEGER.getName());

            Mechanic mechanic = mechanicController.gerMechanicById(mechanicId);
            Spot spot = spotController.getSpotById(spotId);

            orderController.addOrder(new Order(id, requestDate, plannedDate, mechanic, spot));
        }
    }
}
