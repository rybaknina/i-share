package eu.senla.course.annotation.property;

import eu.senla.course.enums.ConfigType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigProperty {
    String file() default "config.properties";
    String key() default "";
    ConfigType type() default ConfigType.STRING;
}
