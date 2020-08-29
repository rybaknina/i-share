package eu.senla.course.action.garage;

import eu.senla.course.api.IAction;
import eu.senla.course.controller.GarageController;
import eu.senla.course.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class ImportGaragesAction implements IAction {
    private final static Logger logger = LogManager.getLogger(ImportGaragesAction.class);
    private GarageController controller = GarageController.getInstance();
    @Override
    public void execute() throws IOException {
        try {
            controller.garagesFromCsv();
        } catch (ServiceException e) {
            logger.error("Error import from csv " + e.getMessage());
        }
    }
}
