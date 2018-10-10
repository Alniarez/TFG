package config;

import java.io.IOException;
import java.util.Properties;

public class AppConfig {

    private final static String APP_PROP = "/app.properties";
    private static Properties props = new Properties();

    public static void init() throws IOException {
        props.load(AppConfig.class.getResourceAsStream(APP_PROP));
    }

    public static String get(String key) {
        String prop = props.getProperty(key);
        if (prop == null)
            throw new RuntimeException(
                    String.format("Property %s is missing. Can't continue.", key));
        return prop;
    }

}
