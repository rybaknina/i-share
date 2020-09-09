package eu.senla.course.controller;

import eu.senla.course.annotation.di.Repository;
import eu.senla.course.annotation.di.Service;
import eu.senla.course.exception.InjectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class BeanController {
    private final Map<Class, Class> diMap = new HashMap<>();
    private final Map<Class, Object> injectedMap = new HashMap<>();
    private final static String START_WITH = "eu.senla.course";
    private final static BeanController instance = new BeanController();
    private final static Logger logger = LogManager.getLogger(BeanController.class);
    private BeanController() {
        try {
            fillDiMap();
        } catch (InjectionException e) {
            logger.error("error message " + e.getMessage());
        }
    }
    public static BeanController getInstance() {
        return instance;
    }

    private void fillDiMap() throws InjectionException {

        try {
            List<Class<?>> classes = LoaderController.getClassesForPackage(START_WITH);
            for (Class clazz: classes) {
                for (Class iClass: clazz.getInterfaces()) {
                    //diMap.put(iClass, clazz);
                    diMap.put(clazz, iClass);
                }
            }
        } catch (ClassNotFoundException e) {
            throw new InjectionException("Load map was broken " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    Object getBean(Class iClass) throws InjectionException {
        Object instance = null;

        for (Map.Entry<Class, Class> entry: diMap.entrySet()) {
            Class implClass = entry.getKey();
            if (iClass.equals(entry.getValue()) && (implClass.isAnnotationPresent(Service.class) || implClass.isAnnotationPresent(Repository.class))) {
                try {
                    instance = implClass.getDeclaredConstructor().newInstance();
                    injectedMap.put(implClass, instance);
                    break;
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    throw new InjectionException(e.getMessage());
                }
            }
        }
        return instance;
    }

    @SuppressWarnings("unchecked")
    Object createInstance(Class clazz) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, InjectionException {
        Object instance;

        try {
            Method method = clazz.getMethod("getInstance");
            instance = method.invoke(null);
        } catch (NoSuchMethodException e) {
            instance = getObject(clazz);
            if (instance == null) {
                Constructor<?> constructor = clazz.getDeclaredConstructor();
                constructor.setAccessible(true);
                instance = constructor.newInstance();
            }
        }

        if (instance == null) {
            throw new InjectionException("Can not create instance");
        }
        return instance;
    }

    Object getObject(Object clazz) {
        return injectedMap.get(clazz);
    }
}
