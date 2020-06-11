package eu.senla.course.action.service;

import eu.senla.course.api.IAction;
import eu.senla.course.entity.Service;
import eu.senla.course.util.InputValidator;
import eu.senla.course.view.Workshop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;

public class AddServiceAction implements IAction {
    @Override
    public void execute() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {

            Integer id = InputValidator.readInteger(reader, "Input Service id");
            String name = InputValidator.readString(reader, "Input Service name");
            Integer hours = InputValidator.readInteger(reader, "Input Hours for Service");
            BigDecimal price = InputValidator.readDecimal(reader, "Input price for an hour");

            Workshop.getInstance().addService(new Service(id, name, hours, price));

        } catch (IOException e) {
            System.out.println("Something wrong happens...");
        }
    }
}
