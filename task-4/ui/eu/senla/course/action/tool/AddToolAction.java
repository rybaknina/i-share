package eu.senla.course.action.tool;

import eu.senla.course.action.constant.ActionHelper;
import eu.senla.course.api.IAction;
import eu.senla.course.controller.ToolController;
import eu.senla.course.entity.Tool;
import eu.senla.course.service.ToolService;
import eu.senla.course.util.InputValidator;

import java.math.BigDecimal;
import java.util.Scanner;

public class AddToolAction implements IAction {
    private ToolController controller = new ToolController(new ToolService());
    @Override
    public void execute() {
        try (Scanner scanner = new Scanner(System.in)) {

            Integer id = InputValidator.readInteger(scanner, ActionHelper.IN_INTEGER.getName());
            String name = InputValidator.readString(scanner, ActionHelper.IN_STRING.getName());
            Integer hours = InputValidator.readInteger(scanner, ActionHelper.IN_INTEGER.getName());
            BigDecimal price = InputValidator.readDecimal(scanner, ActionHelper.IN_BIG_DECIMAL.getName());

            controller.addTool(new Tool(id, name, hours, price));
        }
    }
}
