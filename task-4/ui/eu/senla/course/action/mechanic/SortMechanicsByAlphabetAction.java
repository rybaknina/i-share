package eu.senla.course.action.mechanic;

import eu.senla.course.api.IAction;
import eu.senla.course.controller.MechanicController;
import eu.senla.course.entity.comparator.mechanic.ByAlphabet;
import eu.senla.course.service.MechanicService;

import java.util.Scanner;

public class SortMechanicsByAlphabetAction implements IAction {
    private MechanicController controller = new MechanicController(new MechanicService());
    @Override
    public void execute() {
        try (Scanner scanner = new Scanner(System.in)) {

            controller.sortMechanicsBy(new ByAlphabet());
        }
    }
}
