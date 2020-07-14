package eu.senla.course.action.garage;

import eu.senla.course.api.IAction;
import eu.senla.course.controller.GarageController;
import eu.senla.course.entity.Garage;
import eu.senla.course.enums.ActionHelper;
import eu.senla.course.exception.ServiceException;
import eu.senla.course.util.InputValidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DeleteGarageAction implements IAction {
    private GarageController controller = GarageController.getInstance();
    @Override
    public void execute() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        Integer id = InputValidator.readInteger(reader, ActionHelper.IN_INTEGER.getName());
        Garage garage = controller.getGarageById(id);
        try {
            controller.deleteGarage(garage);
        } catch (ServiceException e) {
            System.err.println("Service exception " + e.getMessage());
        }
    }
}
