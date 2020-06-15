package eu.senla.course.action.spot;

import eu.senla.course.action.constant.ActionHelper;
import eu.senla.course.api.IAction;
import eu.senla.course.controller.GarageController;
import eu.senla.course.controller.SpotController;
import eu.senla.course.entity.Garage;
import eu.senla.course.entity.Spot;
import eu.senla.course.service.ServiceProvider;
import eu.senla.course.util.InputValidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AddSpotAction implements IAction {
    private SpotController spotController = new SpotController(ServiceProvider.getInstance().getSpotService());
    private GarageController garageController = new GarageController(ServiceProvider.getInstance().getGarageService());
    @Override
    public void execute() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        Integer id = InputValidator.readInteger(reader, ActionHelper.IN_INTEGER.getName());
        Integer garageId = InputValidator.readInteger(reader, ActionHelper.IN_INTEGER.getName());
        Garage garage = garageController.getGarageById(garageId);

        spotController.addSpot(new Spot(id, garage));
    }
}
