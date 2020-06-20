package eu.senla.course.action.mechanic;

import eu.senla.course.api.IAction;
import eu.senla.course.controller.MechanicController;

public class GetMechanicsAction implements IAction {
    private MechanicController controller = MechanicController.getInstance();
    @Override
    public void execute() {
        controller.getMechanics().stream().forEach(System.out::println);
    }
}
