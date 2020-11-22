package eu.senla.course.controller;

import eu.senla.course.annotation.di.Injection;
import eu.senla.course.exception.InjectionException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

final public class InjectionController {
    private final static InjectionController instance = new InjectionController();
    private final static String START_WITH = "eu.senla.course";
    private BeanController beanController = BeanController.getInstance();

    private InjectionController() {

    }

    public static InjectionController getInstance() {
        return instance;
    }

    public void inject() throws InjectionException {
        try {
            List<Class <?>> classes = LoaderController.getClassesForPackage(START_WITH);
            for (Class clazz: classes) {
                createInstances(clazz);
            }
        } catch (ClassNotFoundException e) {
            throw new InjectionException(e.getMessage());
        }
    }

    private void createInstances(Class<?> clazz) throws InjectionException {

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
