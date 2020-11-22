package eu.senla.course.action.garage;

import eu.senla.course.api.IAction;
import eu.senla.course.controller.GarageController;
import eu.senla.course.entity.Garage;
import eu.senla.course.enums.ActionHelper;
import eu.senla.course.exception.ServiceException;
import eu.senla.course.util.InputValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AddGarageAction implements IAction {

    private GarageController controller = GarageController.getInstance();
    private final static Logger logger = LogManager.getLogger(AddGarageAction.class);

    @Override
    public void execute() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String name = InputValidator.readString(reader, ActionHelper.IN_STRING.getName());

        try {
            controller.addGarage(new Garage(name));
        } catch (ServiceException e) {
            logger.error("Service exception " + e.getMessage());
        }
    }
}
