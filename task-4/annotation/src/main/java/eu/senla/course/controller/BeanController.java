package eu.senla.course.controller;

import eu.senla.course.annotation.di.Repository;
import eu.senla.course.annotation.di.Service;
import eu.senla.course.exception.InjectionException;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.reflect.ClassPath.from;

class BeanController {
    private final Map<Class, Class> diMap = new HashMap<>();
    private final Map<Class, Object> injectedMap = new HashMap<>();
    private final static String START_WITH = "eu.senla.course";
    private final static BeanController instance = new BeanController();
    private BeanController() {
        try {
            fillDiMap();
        } catch (InjectionException e) {
            System.err.println(e.getMessage());
        }
    }
    public static BeanController getInstance(){
        return instance;
    }

    private void fillDiMap() throws InjectionException {
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
            throw new InjectionException("Load map was broken");
        }
    }

    @SuppressWarnings("unchecked")
    Object getBean(Class iClass) throws InjectionException {
        Object instance = null;
        Class implClass = diMap.get(iClass);
        if (implClass != null && (implClass.isAnnotationPresent(Service.class) || implClass.isAnnotationPresent(Repository.class))) {
            try {
               instance = implClass.getDeclaredConstructor().newInstance();
               injectedMap.put(implClass, instance);

            } catch (InstantiationException|IllegalAccessException|InvocationTargetException|NoSuchMethodException e) {
                throw new InjectionException(e.getMessage());
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

        if (instance == null){
            throw new InjectionException("Can not create instance");
        }
        return instance;
    }

    Object getObject(Object clazz){
        return injectedMap.get(clazz);
    }
}
