package eu.senla.course.action.spot;

import eu.senla.course.action.constant.ActionHelper;
import eu.senla.course.api.IAction;
import eu.senla.course.controller.GarageController;
import eu.senla.course.controller.SpotController;
import eu.senla.course.entity.Garage;
import eu.senla.course.entity.Spot;
import eu.senla.course.service.GarageService;
import eu.senla.course.service.SpotService;
import eu.senla.course.util.InputValidator;

import java.util.Scanner;

public class AddSpotAction implements IAction {
    private SpotController spotController = new SpotController(new SpotService());
    private GarageController garageController = new GarageController(new GarageService());
    @Override
    public void execute() {
        try (Scanner scanner = new Scanner(System.in)) {

            Integer id = InputValidator.readInteger(scanner, ActionHelper.IN_INTEGER.getName());
            Integer garageId = InputValidator.readInteger(scanner, ActionHelper.IN_INTEGER.getName());
            Garage garage = garageController.getGarageById(garageId);

            spotController.addSpot(new Spot(id, garage));
        }
    }
}
