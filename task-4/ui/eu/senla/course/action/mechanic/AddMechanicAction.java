package eu.senla.course.action.mechanic;

import eu.senla.course.action.constant.ActionHelper;
import eu.senla.course.api.IAction;
import eu.senla.course.controller.MechanicController;
import eu.senla.course.entity.Mechanic;
import eu.senla.course.service.ServiceProvider;
import eu.senla.course.util.InputValidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AddMechanicAction implements IAction {
    private MechanicController controller = new MechanicController(ServiceProvider.getInstance().getMechanicService());
    @Override
    public void execute() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        Integer id = InputValidator.readInteger(reader, ActionHelper.IN_INTEGER.getName());
        String name = InputValidator.readString(reader, ActionHelper.IN_STRING.getName());

        controller.addMechanic(new Mechanic(id, name));
    }
}
