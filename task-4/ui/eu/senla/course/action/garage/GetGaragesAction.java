package eu.senla.course.action.garage;

import eu.senla.course.api.IAction;
import eu.senla.course.api.IGarageService;
import eu.senla.course.controller.GarageController;
import eu.senla.course.service.ServiceProvider;

public class GetGaragesAction implements IAction {
    private IGarageService service = ServiceProvider.getInstance().getGarageService();
    private GarageController controller = new GarageController(service);

    @Override
    public void execute() {
        controller.getGarages().forEach(System.out::println);
    }
}
