package eu.senla.course.action.garage;

import eu.senla.course.action.constant.ActionHelper;
import eu.senla.course.api.IAction;
import eu.senla.course.controller.GarageController;
import eu.senla.course.controller.OrderController;
import eu.senla.course.entity.Order;
import eu.senla.course.service.GarageService;
import eu.senla.course.service.OrderService;
import eu.senla.course.util.InputValidator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class ListAvailableSpotsAction implements IAction {
    private GarageController garageController = new GarageController(new GarageService());
    private OrderController orderController = new OrderController(new OrderService());
    @Override
    public void execute() {
        try (Scanner scanner = new Scanner(System.in)) {
            List<Order> orders = orderController.getOrders();
            LocalDateTime futureDate = InputValidator.readDateTime(scanner, ActionHelper.IN_LOCAL_DATE_TIME.getName());

            garageController.listAvailableSpots(futureDate, orders);
        }
    }
}
