package eu.senla.course.action.order;

import eu.senla.course.api.IAction;
import eu.senla.course.controller.MechanicController;
import eu.senla.course.controller.OrderController;
import eu.senla.course.entity.Mechanic;
import eu.senla.course.entity.Order;
import eu.senla.course.enums.ActionHelper;
import eu.senla.course.exception.ServiceException;
import eu.senla.course.util.InputValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FindMechanicOrderAction implements IAction {
    private final static Logger logger = LogManager.getLogger(FindMechanicOrderAction.class);
    private OrderController orderController = OrderController.getInstance();
    private MechanicController mechanicController = MechanicController.getInstance();
    @Override
    public void execute() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        Integer id = InputValidator.readInteger(reader, ActionHelper.IN_INTEGER.getName());
        Mechanic mechanic = mechanicController.getMechanicById(id);
        try {
            Order order = orderController.mechanicOrder(mechanic);
            if (order == null) {
                System.err.println("Order is not found");
            } else {
                System.out.println("Order " + order);
            }
        } catch (ServiceException e) {
            logger.error("Service exception " + e.getMessage());
        }
    }
}
