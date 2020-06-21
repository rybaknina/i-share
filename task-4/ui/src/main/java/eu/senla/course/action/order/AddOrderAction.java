package eu.senla.course.action.order;

import eu.senla.course.api.IAction;
import eu.senla.course.controller.MechanicController;
import eu.senla.course.controller.OrderController;
import eu.senla.course.controller.SpotController;
import eu.senla.course.entity.Mechanic;
import eu.senla.course.entity.Order;
import eu.senla.course.entity.Spot;
import eu.senla.course.enums.ActionHelper;
import eu.senla.course.util.InputValidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;

public class AddOrderAction implements IAction {
    private OrderController orderController = OrderController.getInstance();
    private MechanicController mechanicController = MechanicController.getInstance();
    private SpotController spotController = SpotController.getInstance();
    @Override
    public void execute() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        //Integer id = InputValidator.readInteger(reader, ActionHelper.IN_INTEGER.getName());
        LocalDateTime requestDate = InputValidator.readDateTime(reader, ActionHelper.IN_LOCAL_DATE_TIME.getName());
        LocalDateTime plannedDate = InputValidator.readDateTime(reader, ActionHelper.IN_LOCAL_DATE_TIME.getName());
        Integer mechanicId = InputValidator.readInteger(reader, ActionHelper.IN_INTEGER.getName()) - 1;
        Integer spotId = InputValidator.readInteger(reader, ActionHelper.IN_INTEGER.getName()) - 1;

        Mechanic mechanic = mechanicController.gerMechanicById(mechanicId);

        Spot spot = spotController.getSpotById(spotId);

        orderController.addOrder(new Order(requestDate, plannedDate, mechanic, spot));
    }
}
