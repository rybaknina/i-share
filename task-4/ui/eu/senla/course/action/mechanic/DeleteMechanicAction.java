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

public class DeleteMechanicAction implements IAction {
    private MechanicController controller = new MechanicController(ServiceProvider.getInstance().getMechanicService());
    @Override
    public void execute() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        Integer id = InputValidator.readInteger(reader, ActionHelper.IN_INTEGER.getName()) - 1;
        Mechanic mechanic = controller.gerMechanicById(id);

        controller.deleteMechanic(mechanic);
    }
}
