package eu.senla.course.util;

import java.io.*;
import java.util.Properties;

public class PathToFile {

    private static final String PROPERTY_FILE = "config.properties";

    private static Properties properties = new Properties();
    private final static PathToFile instance = new PathToFile();

    private PathToFile() {
        this.loadProperty();
    }

    public PathToFile getInstance(){
        return instance;
    }

    private void loadProperty(){
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

    public static String getPath(String key){
        return properties.getProperty(key);
    }
}
