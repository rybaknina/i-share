package eu.senla.course.action.tool;

import eu.senla.course.api.IAction;
import eu.senla.course.controller.ToolController;
import eu.senla.course.entity.Tool;
import eu.senla.course.enums.ActionHelper;
import eu.senla.course.util.InputValidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DeleteToolAction implements IAction {
    private ToolController controller = ToolController.getInstance();
    @Override
    public void execute() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        Integer id = InputValidator.readInteger(reader, ActionHelper.IN_INTEGER.getName());
        Tool tool = controller.getToolById(id);
        if (tool != null) {
            controller.deleteTool(tool);
        } else {
            System.err.println("Tool is not found");
        }
    }
}
