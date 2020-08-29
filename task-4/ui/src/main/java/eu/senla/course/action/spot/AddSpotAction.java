package eu.senla.course.action.spot;

import eu.senla.course.api.IAction;
import eu.senla.course.controller.GarageController;
import eu.senla.course.controller.SpotController;
import eu.senla.course.entity.Garage;
import eu.senla.course.entity.Spot;
import eu.senla.course.enums.ActionHelper;
import eu.senla.course.exception.ServiceException;
import eu.senla.course.util.InputValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AddSpotAction implements IAction {
    private final static Logger logger = LogManager.getLogger(AddSpotAction.class);
    private SpotController spotController = SpotController.getInstance();
    private GarageController garageController = GarageController.getInstance();
    @Override
    public void execute() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        Integer garageId = InputValidator.readInteger(reader, ActionHelper.IN_INTEGER.getName());
        try {
            Garage garage = garageController.getGarageById(garageId);
            spotController.addSpot(new Spot(garage));
        } catch (ServiceException e) {
            logger.error("Service exception " + e.getMessage());
        }
    }
}
