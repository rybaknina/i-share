package eu.senla.course.controller;

import eu.senla.course.annotation.property.ConfigProperty;
import eu.senla.course.enums.ConfigType;
import eu.senla.course.exception.AnnotationException;
import eu.senla.course.exception.InjectionException;
import eu.senla.course.util.PathToFile;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;


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
            List<Class<?>> classes = LoaderController.getClassesForPackage(START_WITH);
            for (Class clazz: classes){
                setProperty(clazz);
            }

        } catch (IllegalAccessException | InjectionException | ClassNotFoundException e) {
            throw new AnnotationException(e.getMessage());
        }
    }

    private void setProperty(@NotNull Class<?> clazz) throws IllegalAccessException, AnnotationException, InjectionException {
        for (Field field :clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(ConfigProperty.class)){
                field.setAccessible(true);

                Object obj = Modifier.isStatic(field.getModifiers())? clazz: BeanController.getInstance().getObject(clazz);
                if (obj == null){
                    throw new InjectionException("Instance of class is not exist");
                }

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
                            field.setInt(obj, intValue);
                            break;
                        }
                        case BOOLEAN:{
                            boolean boolValue = Boolean.parseBoolean(path.getPath(key));
                            field.setBoolean(obj, boolValue);
                            break;
                        }
                        default:{
                            String value = path.getPath(key);
                            field.set(obj, value);
                            break;
                        }
                    }

                } else {
                    throw new AnnotationException("Wrong key int the configuration");
                }
            }
        }
    }
}
