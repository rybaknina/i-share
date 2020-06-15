package eu.senla.course.action.garage;

import eu.senla.course.action.constant.ActionHelper;
import eu.senla.course.api.IAction;
import eu.senla.course.controller.GarageController;
import eu.senla.course.entity.Garage;
import eu.senla.course.service.GarageService;
import eu.senla.course.util.InputValidator;

import java.util.Scanner;

public class DeleteGarageAction implements IAction {
    private GarageController controller = new GarageController(new GarageService());
    @Override
    public void execute() {
        try (Scanner scanner = new Scanner(System.in)) {

            Integer id = InputValidator.readInteger(scanner, ActionHelper.IN_INTEGER.getName());
            Garage garage = controller.getGarageById(id);

            controller.deleteGarage(garage);
        }
    }
}
