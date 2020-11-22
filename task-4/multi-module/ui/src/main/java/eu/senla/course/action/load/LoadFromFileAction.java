package eu.senla.course.action.load;

import eu.senla.course.api.IAction;
import eu.senla.course.util.SerializeUtil;
import eu.senla.course.util.exception.SerializeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class LoadFromFileAction implements IAction {
    private final static Logger logger = LogManager.getLogger(LoadFromFileAction.class);

    @Override
    public void execute() throws IOException {
        try {
            SerializeUtil.deserialize();
        } catch (SerializeException e) {
            logger.warn("Loading data from file was broken");
        }
    }
}
