package config;

import java.io.IOException;
import java.util.Properties;

public class MailConfig {

    private final static String MAIL_PROP = "/mail.properties";
    private static Properties props = new Properties();

    public static String get(String key) {
        String prop = props.getProperty(key);
        if (prop == null)
            throw new RuntimeException(
                    String.format("Property %s is missing. Can't continue.", key));
        return prop;
    }

    public static void init() throws IOException {
        props.load(MailConfig.class.getResourceAsStream(MAIL_PROP));
    }

}
