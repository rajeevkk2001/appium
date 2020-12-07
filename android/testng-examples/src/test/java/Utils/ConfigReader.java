package Utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;
    private static final String propertyFilePath = System.getProperty("user.dir")+ "/ConfigFile//config.properties";

     private static void readConfig () {
            BufferedReader reader;
            try {
                reader = new BufferedReader(new FileReader(propertyFilePath));
                properties = new Properties();
                try {
                    properties.load(reader);
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                throw new RuntimeException("config.properties not found at " + propertyFilePath);
            }
        }

    public static String getPlatform() {
        readConfig();
        String platform = properties.getProperty("platform");
        if(platform != null) return platform;
        else throw new RuntimeException("platform not specified in the configuration.properties file.");
    }

}
