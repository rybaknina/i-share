package eu.senla.course.action.order;

import eu.senla.course.api.IAction;
import eu.senla.course.controller.OrderController;
import eu.senla.course.entity.comparator.order.ByPlannedDate;
import eu.senla.course.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CurrentOrdersByPlannedDate implements IAction {
    private final static Logger logger = LogManager.getLogger(CurrentOrdersByPlannedDate.class);
    private OrderController controller = OrderController.getInstance();

    @Override
    public void execute() {
        try {
            controller.listCurrentOrders(new ByPlannedDate()).forEach(System.out::println);
        } catch (ServiceException e) {
            logger.error("Service exception " + e.getMessage());
        }
    }
}