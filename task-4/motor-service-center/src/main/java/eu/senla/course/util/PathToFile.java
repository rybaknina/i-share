package eu.senla.course.util;

import java.io.*;
import java.util.Properties;

public class PathToFile {

    private static final String PROPERTY_FILE = "config.properties";

    private static Properties properties = new Properties();

    public PathToFile() {

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(PROPERTY_FILE)){
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                throw new FileNotFoundException("Property file '" + PROPERTY_FILE + "' not found in the classpath");
            }
        } catch (Exception e){
            System.out.println("Exception " + e.getMessage());
        }

    }

    public String getPath(String key){
        return properties.getProperty(key);
    }
}
