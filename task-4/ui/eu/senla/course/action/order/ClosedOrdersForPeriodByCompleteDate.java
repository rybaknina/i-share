package eu.senla.course.action.order;

import eu.senla.course.action.constant.ActionHelper;
import eu.senla.course.api.IAction;
import eu.senla.course.controller.OrderController;
import eu.senla.course.entity.OrderStatus;
import eu.senla.course.entity.comparator.order.ByCompleteDate;
import eu.senla.course.service.OrderService;
import eu.senla.course.util.InputValidator;

import java.time.LocalDateTime;
import java.util.Scanner;

public class ClosedOrdersForPeriodByCompleteDate implements IAction {
    private OrderController controller = new OrderController(new OrderService());

    @Override
    public void execute() {
        try (Scanner scanner = new Scanner(System.in)) {
            LocalDateTime startDate = InputValidator.readDateTime(scanner, ActionHelper.IN_LOCAL_DATE_TIME.getName());
            LocalDateTime endDate = InputValidator.readDateTime(scanner, ActionHelper.IN_LOCAL_DATE_TIME.getName());

            controller.ordersForPeriod(new ByCompleteDate(), OrderStatus.CLOSE, startDate, endDate).forEach(System.out::println);
        }
    }
}
