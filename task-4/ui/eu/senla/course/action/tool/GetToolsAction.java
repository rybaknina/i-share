package eu.senla.course.action.tool;

import eu.senla.course.api.IAction;
import eu.senla.course.controller.ToolController;
import eu.senla.course.service.ServiceProvider;

public class GetToolsAction implements IAction {
    private ToolController controller = new ToolController(ServiceProvider.getInstance().getToolService());
    @Override
    public void execute() {
        controller.getTools().forEach(System.out::println);
    }
}
