package eu.senla.course.action.garage;

import eu.senla.course.api.IAction;
import eu.senla.course.controller.GarageController;
import eu.senla.course.exception.ServiceException;

import java.io.IOException;

public class ImportGaragesAction implements IAction {
    private GarageController controller = GarageController.getInstance();
    @Override
    public void execute() throws IOException {
        try {
            controller.garagesFromCsv();
        } catch (ServiceException e) {
            System.out.println("Error import from csv");
        }
    }
}
