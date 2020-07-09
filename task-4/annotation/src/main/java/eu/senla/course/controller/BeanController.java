package eu.senla.course.controller;

import eu.senla.course.annotation.di.Repository;
import eu.senla.course.annotation.di.Service;
import eu.senla.course.exception.InjectionException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.reflect.ClassPath.from;

class BeanController {
    private static Map<Class, Class> diMap = new HashMap<>();
    private final static String START_WITH = "eu.senla.course";
    private static final BeanController instance = new BeanController();
    private BeanController() {
        fillDiMap();
    }
    public static BeanController getInstance(){
        return instance;
    }

    private void fillDiMap() {
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();

        try {
            for (final var info : from(loader).getTopLevelClasses()) {
                if (info.getName().startsWith(START_WITH)) {
                    final Class<?> clazz = info.load();
                    for (Class iClass : clazz.getInterfaces()) {
                        diMap.put(iClass, clazz);
                    }
                }
            }
        } catch (IOException e) {
            // ignore
        }
    }

    @SuppressWarnings("unchecked")
    Object getBean(Class iClass) throws InjectionException {
        Object instance = null;
        Class implClass = diMap.get(iClass);
        if (implClass != null && (implClass.isAnnotationPresent(Service.class) || implClass.isAnnotationPresent(Repository.class))) {
            try {
                instance = implClass.getDeclaredConstructor().newInstance();
            } catch (InstantiationException|IllegalAccessException|InvocationTargetException|NoSuchMethodException e) {
                throw new InjectionException(e.getMessage());
            }
        }
        return instance;
    }
}
