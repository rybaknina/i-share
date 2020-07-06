package eu.senla.course.action.spot;

import eu.senla.course.api.IAction;
import eu.senla.course.controller.SpotController;

import java.io.IOException;

public class ExportSpotsAction implements IAction {
    private SpotController spotController = SpotController.getInstance();
    @Override
    public void execute() throws IOException {
        spotController.spotsToCsv();
    }
}
