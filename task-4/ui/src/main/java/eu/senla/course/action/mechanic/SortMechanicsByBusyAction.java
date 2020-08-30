package eu.senla.course.action.mechanic;

import eu.senla.course.api.IAction;
import eu.senla.course.controller.MechanicController;
import eu.senla.course.entity.comparator.mechanic.ByBusy;
import eu.senla.course.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SortMechanicsByBusyAction implements IAction {
    private final static Logger logger = LogManager.getLogger(SortMechanicsByBusyAction.class);
    private MechanicController controller = MechanicController.getInstance();
    @Override
    public void execute() {
        try {
            controller.sortMechanicsBy(new ByBusy());
        } catch (ServiceException e) {
            logger.error("Service exception " + e.getMessage());
        }
    }
}
