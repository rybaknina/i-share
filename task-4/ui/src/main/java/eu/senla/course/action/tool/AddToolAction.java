package eu.senla.course.action.tool;

import eu.senla.course.api.IAction;
import eu.senla.course.controller.ToolController;
import eu.senla.course.entity.Tool;
import eu.senla.course.enums.ActionHelper;
import eu.senla.course.util.InputValidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;

public class AddToolAction implements IAction {
    private ToolController controller = ToolController.getInstance();
    @Override
    public void execute() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String name = InputValidator.readString(reader, ActionHelper.IN_STRING.getName());
        Integer hours = InputValidator.readInteger(reader, ActionHelper.IN_INTEGER.getName());
        BigDecimal price = InputValidator.readDecimal(reader, ActionHelper.IN_BIG_DECIMAL.getName());

        controller.addTool(new Tool(name, hours, price));
    }
}
