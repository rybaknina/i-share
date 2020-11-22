package eu.senla.course.action.order;

import eu.senla.course.api.IAction;
import eu.senla.course.controller.OrderController;
import eu.senla.course.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class ImportOrdersAction implements IAction {
    private final static Logger logger = LogManager.getLogger(ImportOrdersAction.class);
    private OrderController orderController = OrderController.getInstance();
    @Override
    public void execute() throws IOException {
        try {
            orderController.ordersFromCsv();
        } catch (ServiceException e) {
            logger.warn("Error import orders from csv " + e.getMessage());
        }
    }
}
