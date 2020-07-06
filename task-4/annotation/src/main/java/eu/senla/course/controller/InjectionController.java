package eu.senla.course.controller;

import eu.senla.course.annotation.di.Injection;
import eu.senla.course.annotation.di.Service;
import eu.senla.course.exception.InjectionException;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.common.reflect.ClassPath.from;

public class InjectionController {
    private final static InjectionController instance = new InjectionController();
    private final static String START_WITH = "eu.senla.course";

    private InjectionController(){

    }

    public static InjectionController getInstance(){
        return instance;
    }

    public void inject() throws InjectionException {

        try {
            final ClassLoader loader = Thread.currentThread().getContextClassLoader();

            for (final var info : from(loader).getTopLevelClasses()) {
                if (info.getName().startsWith(START_WITH)) {
                    final Class<?> clazz = info.load();

                    injectionScanner(clazz);
                }
            }

        } catch (IOException e) {
            throw new InjectionException(e.getMessage());
        }

    }

    private void injectionScanner(@NotNull Class<?> clazz) throws InjectionException {
        Map<String, Field> fieldMap = Stream.of(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Injection.class))
                .collect(Collectors.toMap(field -> field.getAnnotation(Injection.class).toString(), Function.identity()));
        for (Map.Entry<String, Field> entry: fieldMap.entrySet()){
            if (entry.getValue().getType().isInterface()){
                Field field = entry.getValue();
                Class<?> iClazz = entry.getValue().getType();

                serviceScanner(iClazz, clazz, field);
            }
        }
    }
    private void serviceScanner(Class<?> iClazz, Class<?> oClazz, Field field) throws InjectionException {
        try {
            final ClassLoader loader = Thread.currentThread().getContextClassLoader();

            for (final var info : from(loader).getTopLevelClasses()) {
                if (info.getName().startsWith(START_WITH)) {
                    final Class<?> clazz = info.load();

                    List<Class<?>> interfaces = Arrays.asList(clazz.getInterfaces());

                    if (interfaces.contains(iClazz) && clazz.isAnnotationPresent(Service.class)) {

                        Object instance = clazz.getDeclaredConstructor().newInstance();

                        Field declaredField = oClazz.getDeclaredField(field.getName());
                        declaredField.setAccessible(true);
                        declaredField.set(oClazz, instance);

                    }
                }
            }

        } catch (IOException | IllegalAccessException | NoSuchMethodException | InstantiationException | InvocationTargetException | NoSuchFieldException e) {
            throw new InjectionException(e.getMessage());
        }
    }
}
