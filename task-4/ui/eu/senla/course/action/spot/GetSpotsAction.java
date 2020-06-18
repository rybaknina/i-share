package eu.senla.course.action.spot;

import eu.senla.course.api.IAction;
import eu.senla.course.controller.SpotController;

public class GetSpotsAction implements IAction {
    private SpotController controller = SpotController.getInstance();

    @Override
    public void execute() {
        controller.getSpots().forEach(System.out::println);
    }
}
