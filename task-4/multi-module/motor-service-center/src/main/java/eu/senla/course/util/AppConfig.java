package eu.senla.course.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

public class AppConfig {
    private final static Logger logger = LogManager.getLogger(AppConfig.class);
    private final static String START_WITH = "eu.senla.course";

    public AppConfig() {
        init();
    }

    private void init() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan(START_WITH);
        context.refresh();
        logger.info(Arrays.asList(context.getBeanDefinitionNames()));
    }
}
