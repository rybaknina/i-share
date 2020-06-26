package eu.senla.course.action.garage;

import eu.senla.course.api.IAction;
import eu.senla.course.controller.GarageController;

public class GetGaragesAction implements IAction {
    private GarageController controller = GarageController.getInstance();

    @Override
    public void execute() {
        controller.getGarages().forEach(System.out::println);
    }
}
