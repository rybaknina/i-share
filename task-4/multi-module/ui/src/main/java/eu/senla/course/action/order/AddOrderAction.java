package eu.senla.course.action.order;

import eu.senla.course.api.IAction;
import eu.senla.course.controller.MechanicController;
import eu.senla.course.controller.OrderController;
import eu.senla.course.controller.SpotController;
import eu.senla.course.entity.Mechanic;
import eu.senla.course.entity.Order;
import eu.senla.course.entity.Spot;
import eu.senla.course.enums.ActionHelper;
import eu.senla.course.exception.ServiceException;
import eu.senla.course.util.InputValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;

public class AddOrderAction implements IAction {
    private final static Logger logger = LogManager.getLogger(AddOrderAction.class);
    private OrderController orderController = OrderController.getInstance();
    private MechanicController mechanicController = MechanicController.getInstance();
    private SpotController spotController = SpotController.getInstance();
    @Override
    public void execute() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        LocalDateTime requestDate = InputValidator.readDateTime(reader, ActionHelper.IN_LOCAL_DATE_TIME.getName());
        LocalDateTime plannedDate = InputValidator.readDateTime(reader, ActionHelper.IN_LOCAL_DATE_TIME.getName());
        Integer mechanicId = InputValidator.readInteger(reader, ActionHelper.IN_INTEGER.getName());
        Integer spotId = InputValidator.readInteger(reader, ActionHelper.IN_INTEGER.getName());
        Mechanic mechanic = mechanicController.getMechanicById(mechanicId);
        Spot spot = spotController.getSpotById(spotId);

        try {
            orderController.addOrder(new Order(requestDate, plannedDate, mechanic, spot));
        } catch (ServiceException e) {
            logger.error("Service exception " + e.getMessage());
        }
    }
}