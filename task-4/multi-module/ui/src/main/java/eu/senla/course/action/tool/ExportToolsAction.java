package eu.senla.course.action.tool;

import eu.senla.course.api.IAction;
import eu.senla.course.controller.ToolController;

import java.io.IOException;

public class ExportToolsAction implements IAction {
    private ToolController toolController = ToolController.getInstance();
    @Override
    public void execute() throws IOException {
        toolController.toolsToCsv();
    }
}
