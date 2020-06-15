package eu.senla.course.action.spot;

import eu.senla.course.api.IAction;
import eu.senla.course.controller.SpotController;
import eu.senla.course.service.ServiceProvider;

public class GetSpotsAction implements IAction {
    private SpotController controller = new SpotController(ServiceProvider.getInstance().getSpotService());

    @Override
    public void execute() {
        controller.getSpots();
    }
}
