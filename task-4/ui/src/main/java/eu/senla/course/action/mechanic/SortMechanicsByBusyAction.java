package eu.senla.course.action.mechanic;

import eu.senla.course.api.IAction;
import eu.senla.course.controller.MechanicController;
import eu.senla.course.entity.comparator.mechanic.ByBusy;

public class SortMechanicsByBusyAction implements IAction {
    private MechanicController controller = MechanicController.getInstance();
    @Override
    public void execute() {
        controller.sortMechanicsBy(new ByBusy());
    }
}
