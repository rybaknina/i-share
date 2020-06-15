package eu.senla.course.action.tool;

import eu.senla.course.api.IAction;
import eu.senla.course.controller.ToolController;
import eu.senla.course.service.ToolService;

public class GetToolsAction implements IAction {
    private ToolController controller = new ToolController(new ToolService());
    @Override
    public void execute() {
        controller.getTools();
    }
}
