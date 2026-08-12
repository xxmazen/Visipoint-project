package utils;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.util.Collection;
import java.util.Properties;

public class PropertiesReader {
    public static Properties loadProperties() {
        Properties properties = new Properties();
        try {
            Properties props = new Properties();
            Collection<File> propertyFiles;
            propertyFiles = FileUtils.listFiles(new File("src/main/resources/test-data/data.properties"), new String[]{"properties"}, true);
            propertyFiles.forEach(file -> {
                try {
                    props.load(FileUtils.openInputStream(file));
                } catch (Exception e) {
                    System.err.println("Error loading properties from file: " + file.getAbsolutePath() + " - " + e.getMessage());
                }
                properties.putAll(System.getProperties());
                System.getProperties().putAll(props);
            });
            return properties;
        } catch (Exception e) {
            System.err.println("Error loading properties file: " + e.getMessage());
            return null;
        }
    }
    public static String getProperty(String key) {
       try {
           return System.getProperty(key);
       } catch (Exception e) {
           System.err.println("Error retrieving property: " + key + " - " + e.getMessage());
           return null;
       }
    }
}
