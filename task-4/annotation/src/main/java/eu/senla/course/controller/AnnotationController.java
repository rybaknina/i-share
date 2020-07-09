package eu.senla.course.controller;

import eu.senla.course.annotation.property.ConfigProperty;
import eu.senla.course.enums.ConfigType;
import eu.senla.course.exception.AnnotationException;
import eu.senla.course.util.PathToFile;

import java.io.IOException;
import java.lang.reflect.Field;

import static com.google.common.reflect.ClassPath.from;


public class AnnotationController {

    private final static AnnotationController instance = new AnnotationController();
    private final static String START_WITH = "eu.senla.course";

    private AnnotationController(){
    }

    public static AnnotationController getInstance(){
        return instance;
    }

    public void init() throws AnnotationException {

        try {
            final ClassLoader loader = Thread.currentThread().getContextClassLoader();

            for (final var info : from(loader).getTopLevelClasses()) {
                        if (info.getName().startsWith(START_WITH)) {
                            final Class<?> clazz = info.load();
                            setProperty(clazz);
                }
            }

        } catch (IllegalAccessException | IOException e) {
            throw new AnnotationException(e.getMessage());
        }
    }

    private static void setProperty(Class<?> clazz) throws IllegalAccessException, AnnotationException {
        for (Field field :clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(ConfigProperty.class)){
                field.setAccessible(true);

                String file = field.getAnnotation(ConfigProperty.class).file();
                String key = field.getAnnotation(ConfigProperty.class).key();
                ConfigType type = field.getAnnotation(ConfigProperty.class).type();

                if (file.isBlank()){
                    throw new AnnotationException("Config file is not found");
                }
                PathToFile path = new PathToFile(file);
                if (!key.isBlank()) {
                    switch (type){
                        case INT:{
                            int intValue = Integer.parseInt(path.getPath(key));
                            field.setInt(clazz, intValue);
                            break;
                        }
                        case BOOLEAN:{
                            boolean boolValue = Boolean.parseBoolean(path.getPath(key));
                            field.setBoolean(clazz, boolValue);
                            break;
                        }
                        default:{
                            field.set(clazz, path.getPath(key));
                            break;
                        }
                    }

                }
                else {
                    throw new AnnotationException("Wrong key int the configuration");
                }

            }
        }
    }

}
