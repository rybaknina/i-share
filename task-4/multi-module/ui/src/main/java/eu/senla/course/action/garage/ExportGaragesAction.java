package eu.senla.course.action.garage;

import eu.senla.course.api.IAction;
import eu.senla.course.controller.GarageController;

import java.io.IOException;

public class ExportGaragesAction implements IAction {
    private GarageController controller = GarageController.getInstance();
    @Override
    public void execute() throws IOException {
        controller.garagesToCsv();
    }
}
