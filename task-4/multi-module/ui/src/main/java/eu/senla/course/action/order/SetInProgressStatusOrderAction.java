package eu.senla.course.action.order;

import eu.senla.course.api.IAction;
import eu.senla.course.controller.OrderController;
import eu.senla.course.entity.Order;
import eu.senla.course.enums.ActionHelper;
import eu.senla.course.enums.OrderStatus;
import eu.senla.course.exception.ServiceException;
import eu.senla.course.util.InputValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SetInProgressStatusOrderAction implements IAction {
    private final static Logger logger = LogManager.getLogger(SetInProgressStatusOrderAction.class);
    private OrderController controller = OrderController.getInstance();
    @Override
    public void execute() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        Integer id = InputValidator.readInteger(reader, ActionHelper.IN_INTEGER.getName());
        Order order = controller.getOrderById(id);
        try {
            controller.changeStatusOrder(order, OrderStatus.IN_PROGRESS);
        } catch (ServiceException e) {
            logger.error("Service exception " + e.getMessage());
        }
    }
}
