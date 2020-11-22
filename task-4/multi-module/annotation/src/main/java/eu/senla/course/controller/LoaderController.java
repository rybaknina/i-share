package eu.senla.course.controller;

import eu.senla.course.exception.InjectionException;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.jar.JarEntry;

class LoaderController {
    private final static String END_WITH = ".class";

    private static void checkDirectory(File directory, String packageName, List<Class<?>> classes) throws ClassNotFoundException {

        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();

            for (File file : Objects.requireNonNull(files)) {
                if (file.getName().endsWith(END_WITH)) {
                    classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
                } else if (file.isDirectory()) {
                    checkDirectory(file, packageName + "." + file.getName(), classes);
                }
            }
        }
    }
    private static void checkJarFile(JarURLConnection connection, String packageName, List<Class<?>> classes) throws ClassNotFoundException, IOException {

        final Enumeration<JarEntry> entries = connection.getJarFile().entries();

        while (entries.hasMoreElements()) {
            JarEntry jarEntry = entries.nextElement();
            if (jarEntry != null && jarEntry.getName().contains(END_WITH)) {
                String entryName = jarEntry.getName().substring(0, jarEntry.getName().length() - 6).replace('/', '.');

                if (entryName.contains(packageName)) {
                    classes.add(Class.forName(entryName));
                }
            }
        }
    }

    static List<Class<?>> getClassesForPackage(String packageName) throws ClassNotFoundException, InjectionException {
        final List<Class<?>> classes = new ArrayList<>();

        try {
            final ClassLoader loader = Thread.currentThread().getContextClassLoader();

            final Enumeration<URL> resources = loader.getResources(packageName.replace('.', '/'));

            while (resources.hasMoreElements()) {

                URL url = resources.nextElement();
                URLConnection connection = url.openConnection();

                if (connection instanceof JarURLConnection) {
                    checkJarFile((JarURLConnection) connection, packageName, classes);
                } else {
                    checkDirectory(new File(url.getPath()), packageName, classes);
                }
            }
        } catch (IOException e) {
            throw new InjectionException("Error loading classes " + e.getMessage());
        }

        return classes;
    }
}
