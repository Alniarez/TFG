package config;

import org.sql2o.Sql2o;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DatabaseConfig {

    private final static String SQL_PROP = "/sql.properties";
    private static ThreadLocal<Sql2o> threadLocal = new ThreadLocal<>();
    private static Properties props = new Properties();

    public static Sql2o getSql2oConnection() {
        Sql2o connection = threadLocal.get();
        if (connection == null)
            connection = createSql2oConnection();

        return connection;
    }

    public static void forceTestingSql2oConnection(Sql2o connection) {
        threadLocal.set(connection);
    }

    private static Sql2o createSql2oConnection() {
        Sql2o sql2o = new Sql2o(get("SQL_URL"), get("DB_USER"), get("DB_PASS"));

        // Map<Nombre Columna, Nombre variable>
        Map<String, String> colMaps = new HashMap<String, String>();
        colMaps.put("user_id", "userId");
        colMaps.put("thread_id", "threadId");
        colMaps.put("subject_id", "subjectId");
        colMaps.put("psw", "password");

        sql2o.setDefaultColumnMappings(colMaps);

        threadLocal.set(sql2o);
        return sql2o;
    }

    public static String get(String key) {
        String prop = props.getProperty(key);
        if (prop == null)
            throw new RuntimeException(
                    String.format("Property %s is missing. Can't continue.", key));
        return prop;
    }

    public static void init() throws IOException {
        props.load(DatabaseConfig.class.getResourceAsStream(SQL_PROP));
    }

}
