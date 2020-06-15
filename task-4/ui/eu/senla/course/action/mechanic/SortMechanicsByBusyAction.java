package eu.senla.course.action.mechanic;

import eu.senla.course.api.IAction;
import eu.senla.course.controller.MechanicController;
import eu.senla.course.entity.comparator.mechanic.ByBusy;
import eu.senla.course.service.ServiceProvider;

public class SortMechanicsByBusyAction implements IAction {
    private MechanicController controller = new MechanicController(ServiceProvider.getInstance().getMechanicService());
    @Override
    public void execute() {
        controller.sortMechanicsBy(new ByBusy());
    }
}
