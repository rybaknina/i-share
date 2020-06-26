package eu.senla.course.action.mechanic;

import eu.senla.course.api.IAction;
import eu.senla.course.controller.MechanicController;
import eu.senla.course.entity.comparator.mechanic.ByBusy;
import eu.senla.course.exception.ServiceException;

public class SortMechanicsByBusyAction implements IAction {
    private MechanicController controller = MechanicController.getInstance();
    @Override
    public void execute() {
        try {
            controller.sortMechanicsBy(new ByBusy());
        } catch (ServiceException e) {
            System.err.println("Service exception " + e.getMessage());
        }
    }
}
