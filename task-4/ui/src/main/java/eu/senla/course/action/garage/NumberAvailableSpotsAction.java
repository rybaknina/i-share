package eu.senla.course.action.garage;

import eu.senla.course.api.IAction;
import eu.senla.course.controller.GarageController;
import eu.senla.course.controller.OrderController;
import eu.senla.course.entity.Order;
import eu.senla.course.enums.ActionHelper;
import eu.senla.course.exception.ServiceException;
import eu.senla.course.util.InputValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.List;

public class NumberAvailableSpotsAction implements IAction {
    private final static Logger logger = LogManager.getLogger(NumberAvailableSpotsAction.class);
    private GarageController garageController = GarageController.getInstance();
    private OrderController orderController = OrderController.getInstance();
    @Override
    public void execute() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        List<Order> orders = orderController.getOrders();
        LocalDateTime futureDate = InputValidator.readDateTime(reader, ActionHelper.IN_LOCAL_DATE_TIME.getName());

        try {
            garageController.numberAvailableSpots(futureDate, orders);
        } catch (ServiceException e) {
            logger.error("Service exception " + e.getMessage());
        }
    }
}
