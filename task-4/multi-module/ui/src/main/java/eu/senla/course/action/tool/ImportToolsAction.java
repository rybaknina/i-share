package eu.senla.course.action.tool;

import eu.senla.course.api.IAction;
import eu.senla.course.controller.ToolController;
import eu.senla.course.exception.ServiceException;

import java.io.IOException;

public class ImportToolsAction implements IAction {
    private ToolController toolController = ToolController.getInstance();
    @Override
    public void execute() throws IOException {
        try {
            toolController.toolsFromCsv();
        } catch (ServiceException e) {
            System.err.println("Error import tools from csv");
        }
    }
}
