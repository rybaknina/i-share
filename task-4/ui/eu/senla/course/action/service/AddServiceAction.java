package eu.senla.course.action.service;

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

            Integer id = InputValidator.readInteger(scanner, "Input Service id");
            String name = InputValidator.readString(scanner, "Input Service name");
            Integer hours = InputValidator.readInteger(scanner, "Input Hours for Service");
            BigDecimal price = InputValidator.readDecimal(scanner, "Input price for an hour");

            ManagerProvider.getInstance().getServiceManager().addService(new Service(id, name, hours, price));
        }
    }
}
