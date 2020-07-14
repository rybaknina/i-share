package eu.senla.course.controller;

import eu.senla.course.annotation.di.Injection;
import eu.senla.course.exception.InjectionException;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import static com.google.common.reflect.ClassPath.from;

public class InjectionController {
    private final static InjectionController instance = new InjectionController();
    private final static String START_WITH = "eu.senla.course";
    private BeanController beanController = BeanController.getInstance();

    private InjectionController(){

    }

    public static InjectionController getInstance(){
        return instance;
    }

    public void inject() throws InjectionException {
        try {
            final ClassLoader loader = Thread.currentThread().getContextClassLoader();

            for (var info : from(loader).getTopLevelClasses()) {
                if (info.getName().startsWith(START_WITH)) {
                    final Class<?> clazz = info.load();
                    createInstances(clazz);
                }
            }
        } catch (IOException e) {
            throw new InjectionException(e.getMessage());
        }
    }

    private void createInstances(@NotNull Class<?> clazz) throws InjectionException {

        try {
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(Injection.class)) {
                    Class<?> iClazz = field.getType();
                    Object instance = beanController.getBean(iClazz);
                    if (instance != null) {
                        Object containerClass = beanController.createInstance(clazz);

                        Field declaredField = clazz.getDeclaredField(field.getName());
                        declaredField.setAccessible(true);
                        declaredField.set(containerClass, instance);
                    }
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException e) {
            throw new InjectionException(e.getMessage());
        }
    }
}
