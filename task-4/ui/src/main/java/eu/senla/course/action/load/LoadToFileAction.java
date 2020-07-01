package eu.senla.course.action.load;

import eu.senla.course.api.IAction;
import eu.senla.course.util.SerializeUtil;
import eu.senla.course.util.exception.SerializeException;

import java.io.IOException;

public class LoadToFileAction implements IAction {

    @Override
    public void execute() throws IOException {
        try {
            SerializeUtil.serialize();
        } catch (SerializeException e) {
            System.out.println("Loading data to objects was broken");
        }
    }
}
