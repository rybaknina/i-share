package eu.senla.course.action.garage;

import eu.senla.course.api.IAction;
import eu.senla.course.controller.GarageController;
import eu.senla.course.service.GarageService;

import java.util.Scanner;

public class GetGaragesAction implements IAction {
    private GarageController controller = new GarageController(new GarageService());
    @Override
    public void execute() {
        try (Scanner scanner = new Scanner(System.in)) {

            controller.getGarages();
        }
    }
}
