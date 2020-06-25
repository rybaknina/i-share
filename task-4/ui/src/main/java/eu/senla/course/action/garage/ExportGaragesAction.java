package eu.senla.course.action.garage;

import eu.senla.course.api.IAction;
import eu.senla.course.controller.GarageController;
import eu.senla.course.exception.ServiceException;

import java.io.IOException;

public class ExportGaragesAction implements IAction {
    private GarageController controller = GarageController.getInstance();
    @Override
    public void execute() throws IOException {
        try {
            controller.garagesToCsv();
        } catch (ServiceException e) {
            System.out.println("Error export to csv");
        }
    }
}
