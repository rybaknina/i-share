package eu.senla.course.action.tool;

import eu.senla.course.api.IAction;
import eu.senla.course.controller.ToolController;

public class GetToolsAction implements IAction {
    private ToolController controller = ToolController.getInstance();
    @Override
    public void execute() {
        controller.getTools().forEach(System.out::println);
    }
}
