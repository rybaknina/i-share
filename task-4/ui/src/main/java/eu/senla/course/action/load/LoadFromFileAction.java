package eu.senla.course.action.load;

import eu.senla.course.api.IAction;
import eu.senla.course.util.SerializeUtil;
import eu.senla.course.util.exception.SerializeException;

import java.io.IOException;

public class LoadFromFileAction implements IAction {

    @Override
    public void execute() throws IOException {
        try {
            SerializeUtil.deserialize();
        } catch (SerializeException e) {
            System.out.println("Loading data from file was broken");
        }
    }
}
