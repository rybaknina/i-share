package eu.senla.course.action.order;

import eu.senla.course.api.IAction;
import eu.senla.course.controller.OrderController;
import eu.senla.course.entity.OrderStatus;
import eu.senla.course.entity.comparator.order.ByRequestDate;
import eu.senla.course.enums.ActionHelper;
import eu.senla.course.util.InputValidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;

public class DeletedOrdersForPeriodByRequestDate implements IAction {
    private OrderController controller = OrderController.getInstance();

    @Override
    public void execute() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        LocalDateTime startDate = InputValidator.readDateTime(reader, ActionHelper.IN_LOCAL_DATE_TIME.getName());
        LocalDateTime endDate = InputValidator.readDateTime(reader, ActionHelper.IN_LOCAL_DATE_TIME.getName());

        controller.ordersForPeriod(new ByRequestDate(), OrderStatus.DELETE, startDate, endDate).forEach(System.out::println);
    }
}