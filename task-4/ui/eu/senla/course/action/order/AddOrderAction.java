package eu.senla.course.action.order;

import eu.senla.course.action.constant.ActionHelper;
import eu.senla.course.api.IAction;
import eu.senla.course.entity.Mechanic;
import eu.senla.course.entity.Order;
import eu.senla.course.entity.Spot;
import eu.senla.course.service.ManagerProvider;
import eu.senla.course.util.InputValidator;

import java.time.LocalDateTime;
import java.util.Scanner;

public class AddOrderAction implements IAction {
    @Override
    public void execute() {
        try (Scanner scanner = new Scanner(System.in)) {

            Integer id = InputValidator.readInteger(scanner, ActionHelper.IN_INTEGER.getName());
            LocalDateTime requestDate = InputValidator.readDateTime(scanner, ActionHelper.IN_LOCAL_DATE_TIME.getName());
            LocalDateTime plannedDate = InputValidator.readDateTime(scanner, ActionHelper.IN_LOCAL_DATE_TIME.getName());
            Integer mechanicId = InputValidator.readInteger(scanner, ActionHelper.IN_INTEGER.getName());
            Integer spotId = InputValidator.readInteger(scanner, ActionHelper.IN_INTEGER.getName());

            Mechanic mechanic = ManagerProvider.getInstance().getMechanicManager().gerMechanicById(mechanicId);
            Spot spot = ManagerProvider.getInstance().getSpotManager().getSpotById(spotId);

            ManagerProvider.getInstance().getOrderManager().addOrder(new Order(id, requestDate, plannedDate, mechanic, spot));
        }
    }
}
