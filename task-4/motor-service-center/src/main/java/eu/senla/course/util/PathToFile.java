package eu.senla.course.util;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

public class PathToFile {

    private static Properties properties = new Properties();

    public PathToFile(String file) {
        this.loadProperty(file);
    }

    private void loadProperty(String file){
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(file)){
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                throw new FileNotFoundException("Property file '" + file + "' not found in the classpath");
            }
        } catch (Exception e){
            System.out.println("Exception " + e.getMessage());
        }
    }

    public String getPath(String key){
        return properties.getProperty(key);
    }
}
