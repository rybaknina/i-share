package eu.senla.course.action.garage;

import eu.senla.course.action.constant.ActionHelper;
import eu.senla.course.api.IAction;
import eu.senla.course.api.IGarageService;
import eu.senla.course.controller.GarageController;
import eu.senla.course.entity.Garage;
import eu.senla.course.service.ServiceProvider;
import eu.senla.course.util.InputValidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AddGarageAction implements IAction {
    private IGarageService service = ServiceProvider.getInstance().getGarageService();
    private GarageController controller = new GarageController(service);
    @Override
    public void execute() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Integer id = InputValidator.readInteger(reader, ActionHelper.IN_INTEGER.getName());

        controller.addGarage(new Garage(id));
    }
}
