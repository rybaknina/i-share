package eu.senla.course.action.spot;

import eu.senla.course.action.constant.ActionHelper;
import eu.senla.course.api.IAction;
import eu.senla.course.controller.SpotController;
import eu.senla.course.entity.Spot;
import eu.senla.course.service.SpotService;
import eu.senla.course.util.InputValidator;

import java.util.Scanner;

public class DeleteSpotAction implements IAction {
    private SpotController controller = new SpotController(new SpotService());
    @Override
    public void execute() {
        try (Scanner scanner = new Scanner(System.in)) {

            Integer id = InputValidator.readInteger(scanner, ActionHelper.IN_INTEGER.getName());
            Spot spot = controller.getSpotById(id);

            controller.deleteSpot(spot);
        }
    }
}
