package eu.senla.course.action.garage;

import eu.senla.course.action.constant.ActionHelper;
import eu.senla.course.api.IAction;
import eu.senla.course.entity.Garage;
import eu.senla.course.service.ServiceProvider;
import eu.senla.course.util.InputValidator;
import java.util.Scanner;

public class AddGarageAction implements IAction {
    @Override
    public void execute() {
        try (Scanner scanner = new Scanner(System.in)) {

            Integer id = InputValidator.readInteger(scanner, ActionHelper.IN_INTEGER.getName());

            ServiceProvider.getInstance().getGarageManager().addGarage(new Garage(id));
        }
    }
}
