package eu.senla.course.action.garage;

import eu.senla.course.action.constant.ActionHelper;
import eu.senla.course.api.IAction;
import eu.senla.course.controller.GarageController;
import eu.senla.course.controller.OrderController;
import eu.senla.course.entity.Order;
import eu.senla.course.service.ServiceProvider;
import eu.senla.course.util.InputValidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.List;

public class NumberAvailableSpotsAction implements IAction {
    private GarageController garageController = new GarageController(ServiceProvider.getInstance().getGarageService());
    private OrderController orderController = new OrderController(ServiceProvider.getInstance().getOrderService());
    @Override
    public void execute() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        List<Order> orders = orderController.getOrders();
        LocalDateTime futureDate = InputValidator.readDateTime(reader, ActionHelper.IN_LOCAL_DATE_TIME.getName());

        garageController.numberAvailableSpots(futureDate, orders);

    }
}
