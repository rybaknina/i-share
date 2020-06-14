package eu.senla.course.action.service;

import eu.senla.course.action.constant.ActionHelper;
import eu.senla.course.api.IAction;
import eu.senla.course.entity.Service;
import eu.senla.course.service.ManagerProvider;
import eu.senla.course.util.InputValidator;

import java.math.BigDecimal;
import java.util.Scanner;

public class AddServiceAction implements IAction {
    @Override
    public void execute() {
        try (Scanner scanner = new Scanner(System.in)) {

            Integer id = InputValidator.readInteger(scanner, ActionHelper.IN_INTEGER.getName());
            String name = InputValidator.readString(scanner, ActionHelper.IN_STRING.getName());
            Integer hours = InputValidator.readInteger(scanner, ActionHelper.IN_INTEGER.getName());
            BigDecimal price = InputValidator.readDecimal(scanner, ActionHelper.IN_BIG_DECIMAL.getName());

            ManagerProvider.getInstance().getServiceManager().addService(new Service(id, name, hours, price));
        }
    }
}
