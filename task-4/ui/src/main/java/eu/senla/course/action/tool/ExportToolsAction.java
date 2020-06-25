package eu.senla.course.action.tool;

import eu.senla.course.api.IAction;
import eu.senla.course.controller.ToolController;
import eu.senla.course.exception.ServiceException;

import java.io.IOException;

public class ExportToolsAction implements IAction {
    private ToolController toolController = ToolController.getInstance();
    @Override
    public void execute() throws IOException {
        try {
            toolController.toolsToCsv();
        } catch (ServiceException e) {
            System.err.println("Error export tools from csv");
        }

    }
}
