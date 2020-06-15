package eu.senla.course.action.order;

import eu.senla.course.action.constant.ActionHelper;
import eu.senla.course.api.IAction;
import eu.senla.course.controller.GarageController;
import eu.senla.course.controller.OrderController;
import eu.senla.course.entity.Garage;
import eu.senla.course.service.GarageService;
import eu.senla.course.service.OrderService;
import eu.senla.course.util.InputValidator;

import java.time.LocalDate;
import java.util.Scanner;

public class NextAvailableDateAction implements IAction {
    private OrderController orderController = new OrderController(new OrderService());
    private GarageController garageController = new GarageController(new GarageService());
    @Override
    public void execute() {
        try (Scanner scanner = new Scanner(System.in)) {

            Integer garageId = InputValidator.readInteger(scanner, ActionHelper.IN_INTEGER.getName());
            LocalDate endDate = InputValidator.readDate(scanner, ActionHelper.IN_LOCAL_DATE.getName());

            Garage garage = garageController.getGarageById(garageId);

            System.out.println(orderController.nextAvailableDate(new GarageService(), endDate));
        }
    }
}
