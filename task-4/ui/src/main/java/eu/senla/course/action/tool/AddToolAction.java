package eu.senla.course.action.tool;

import eu.senla.course.api.IAction;
import eu.senla.course.controller.OrderController;
import eu.senla.course.controller.ToolController;
import eu.senla.course.entity.Order;
import eu.senla.course.entity.Tool;
import eu.senla.course.enums.ActionHelper;
import eu.senla.course.exception.ServiceException;
import eu.senla.course.util.InputValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;

public class AddToolAction implements IAction {
    private final static Logger logger = LogManager.getLogger(AddToolAction.class);
    private ToolController controller = ToolController.getInstance();
    @Override
    public void execute() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        Integer orderId = InputValidator.readInteger(reader, ActionHelper.IN_INTEGER.getName());
        String name = InputValidator.readString(reader, ActionHelper.IN_STRING.getName());
        Integer hours = InputValidator.readInteger(reader, ActionHelper.IN_INTEGER.getName());
        BigDecimal price = InputValidator.readDecimal(reader, ActionHelper.IN_BIG_DECIMAL.getName());

        Order order = OrderController.getInstance().getOrderById(orderId);

        try {
            controller.addTool(new Tool(name, hours, price, order));
        } catch (ServiceException e) {
            logger.error("Service exception " + e.getMessage());
        }
    }
}
