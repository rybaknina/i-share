package eu.senla.course.action.tool;

import eu.senla.course.action.constant.ActionHelper;
import eu.senla.course.api.IAction;
import eu.senla.course.controller.ToolController;
import eu.senla.course.entity.Tool;
import eu.senla.course.service.ToolService;
import eu.senla.course.util.InputValidator;

import java.util.Scanner;

public class DeleteToolAction implements IAction {
    private ToolController controller = new ToolController(new ToolService());
    @Override
    public void execute() {
        try (Scanner scanner = new Scanner(System.in)) {

            Integer id = InputValidator.readInteger(scanner, ActionHelper.IN_INTEGER.getName());
            Tool tool = controller.getToolBiId(id);

            controller.deleteTool(tool);
        }
    }
}
