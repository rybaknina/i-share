package eu.senla.course.action.mechanic;

import eu.senla.course.api.IAction;
import eu.senla.course.controller.GarageController;
import eu.senla.course.controller.MechanicController;
import eu.senla.course.entity.Garage;
import eu.senla.course.entity.Mechanic;
import eu.senla.course.enums.ActionHelper;
import eu.senla.course.util.InputValidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AddMechanicAction implements IAction {
    private MechanicController mechanicController = MechanicController.getInstance();
    private GarageController garageController = GarageController.getInstance();
    @Override
    public void execute() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String name = InputValidator.readString(reader, ActionHelper.IN_STRING.getName());
        Integer id = InputValidator.readInteger(reader, ActionHelper.IN_INTEGER.getName()) - 1;
        Garage garage = garageController.getGarageById(id);

        mechanicController.addMechanic(new Mechanic(name, garage));
    }
}
