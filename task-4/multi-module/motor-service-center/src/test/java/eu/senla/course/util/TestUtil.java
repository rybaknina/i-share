package eu.senla.course.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;

public class TestUtil {
    private final static Logger logger = LogManager.getLogger(TestUtil.class);
    public static void resetSingleton(Class clazz) {
        try {
            Field instance = clazz.getDeclaredField("instance");
            instance.setAccessible(true);
            instance.set(null, null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            logger.error(e.getMessage());
        }
    }

    public static void setMock(Object mock, Class clazz) {
        try {
            Field instance = clazz.getDeclaredField("instance");
            instance.setAccessible(true);
            instance.set(instance, mock);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
