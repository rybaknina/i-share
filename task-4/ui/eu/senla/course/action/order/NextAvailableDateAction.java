package eu.senla.course.action.order;

import eu.senla.course.action.constant.ActionHelper;
import eu.senla.course.api.IAction;
import eu.senla.course.controller.OrderController;
import eu.senla.course.service.ServiceProvider;
import eu.senla.course.util.InputValidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class NextAvailableDateAction implements IAction {
    private OrderController orderController = new OrderController(ServiceProvider.getInstance().getOrderService());
    @Override
    public void execute() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        LocalDate endDate = InputValidator.readDate(reader, ActionHelper.IN_LOCAL_DATE.getName());

        LocalDateTime nextDate = orderController.nextAvailableDate(ServiceProvider.getInstance().getGarageService(), endDate);
        if (nextDate != null) {
            System.out.println(nextDate.format(DateTimeFormatter.ofPattern("d.MM.uuuu HH:mm")));
        }
    }
}
