package eu.senla.course.action.spot;

import eu.senla.course.api.IAction;
import eu.senla.course.controller.GarageController;
import eu.senla.course.controller.SpotController;
import eu.senla.course.entity.Garage;
import eu.senla.course.entity.Spot;
import eu.senla.course.enums.ActionHelper;
import eu.senla.course.exception.ServiceException;
import eu.senla.course.util.InputValidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AddSpotAction implements IAction {
    private SpotController spotController = SpotController.getInstance();
    private GarageController garageController = GarageController.getInstance();
    @Override
    public void execute() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        Integer garageId = InputValidator.readInteger(reader, ActionHelper.IN_INTEGER.getName()) - 1;
        try {
            Garage garage = garageController.getGarageById(garageId);
            spotController.addSpot(new Spot(garage));
        } catch (ServiceException e) {
            System.err.println("Service exception " + e.getMessage());
        }


    }
}
