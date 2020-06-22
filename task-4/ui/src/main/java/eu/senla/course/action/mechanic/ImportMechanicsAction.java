package eu.senla.course.action.mechanic;

import eu.senla.course.api.IAction;
import eu.senla.course.controller.MechanicController;
import eu.senla.course.service.ServiceException;

import java.io.IOException;

public class ImportMechanicsAction implements IAction {
    private MechanicController mechanicController = MechanicController.getInstance();
    @Override
    public void execute() throws IOException {
        try {
            mechanicController.mechanicsFromCsv();
        } catch (ServiceException e) {
            System.err.println("Error import mechanics from csv");
        }

    }
}
