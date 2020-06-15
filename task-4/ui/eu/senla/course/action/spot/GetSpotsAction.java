package eu.senla.course.action.spot;

import eu.senla.course.api.IAction;
import eu.senla.course.controller.SpotController;
import eu.senla.course.service.SpotService;

public class GetSpotsAction implements IAction {
    private SpotController controller = new SpotController(new SpotService());

    @Override
    public void execute() {
        controller.getSpots();
    }
}
