package eu.senla.course.action.mechanic;

import eu.senla.course.action.constant.ActionHelper;
import eu.senla.course.api.IAction;
import eu.senla.course.entity.Mechanic;
import eu.senla.course.service.ManagerProvider;
import eu.senla.course.util.InputValidator;

import java.util.Scanner;

public class AddMechanicAction implements IAction {
    @Override
    public void execute() {
        try (Scanner scanner = new Scanner(System.in)) {

            Integer id = InputValidator.readInteger(scanner, ActionHelper.IN_INTEGER.getName());
            String name = InputValidator.readString(scanner, ActionHelper.IN_STRING.getName());

            ManagerProvider.getInstance().getMechanicManager().addMechanic(new Mechanic(id, name));
        }
    }
}
