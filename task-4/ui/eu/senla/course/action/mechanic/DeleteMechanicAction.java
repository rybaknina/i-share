package eu.senla.course.action.mechanic;

import eu.senla.course.action.constant.ActionHelper;
import eu.senla.course.api.IAction;
import eu.senla.course.controller.MechanicController;
import eu.senla.course.entity.Mechanic;
import eu.senla.course.service.MechanicService;
import eu.senla.course.util.InputValidator;

import java.util.Scanner;

public class DeleteMechanicAction implements IAction {
    private MechanicController controller = new MechanicController(new MechanicService());
    @Override
    public void execute() {
        try (Scanner scanner = new Scanner(System.in)) {

            Integer id = InputValidator.readInteger(scanner, ActionHelper.IN_INTEGER.getName());
            Mechanic mechanic = controller.gerMechanicById(id);

            controller.deleteMechanic(mechanic);
        }
    }
}
