package eu.senla.course.action.order;

import eu.senla.course.api.IAction;
import eu.senla.course.controller.OrderController;
import eu.senla.course.entity.comparator.order.ByCompleteDate;
import eu.senla.course.enums.ActionHelper;
import eu.senla.course.enums.OrderStatus;
import eu.senla.course.exception.ServiceException;
import eu.senla.course.util.InputValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;

public class CanceledOrdersForPeriodByCompleteDate implements IAction {
    private final static Logger logger = LogManager.getLogger(CanceledOrdersForPeriodByCompleteDate.class);
    private OrderController controller = OrderController.getInstance();

    @Override
    public void execute() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        LocalDateTime startDate = InputValidator.readDateTime(reader, ActionHelper.IN_LOCAL_DATE_TIME.getName());
        LocalDateTime endDate = InputValidator.readDateTime(reader, ActionHelper.IN_LOCAL_DATE_TIME.getName());

        try {
            controller.ordersForPeriod(new ByCompleteDate(), OrderStatus.CANCEL, startDate, endDate).forEach(System.out::println);
        } catch (ServiceException e) {
            logger.error("Service exception " + e.getMessage());
        }
    }
}
