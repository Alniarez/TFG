package app;

import config.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.exception.PersistenceException;
import util.ExceptionUtil;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class App {

    public static Logger logger;

    public static void main(String[] args) {
        List<String> arg = Arrays.asList(args);

        startLogger(arg);

        App app = new App();

        try {
            if (arg.isEmpty())
                // Sin agumentos: run
                app.run();
            else {
                // Con argumentos
                if (arg.contains("config"))
                    app.install(arg);
                if (arg.contains("run"))
                    app.run();

            }
        } catch (IOException e) {
            logger.info("Unable to start.");
            logger.error("One or more properties files is missing or incorrect.");
            logger.debug("Error caused by " + e.toString() + " : " + e.getMessage() + ".");
            logger.debug(ExceptionUtil.getStackTraceAsString(e));
        }
    }

    private static void startLogger(List<String> arg) {
        // ERROR > WARN > INFO > DEBUG > TRACE.

        if (arg.isEmpty()) {
            // Sin argumentos: INFO
            System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "INFO");
        } else {
            argumento: {
                if (arg.contains("error")) {
                    System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "ERROR");
                    break argumento;
                }
                if (arg.contains("warn")) {
                    System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "WARN");
                    break argumento;
                }
                if (arg.contains("info")) {
                    System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "INFO");
                    break argumento;
                }
                if (arg.contains("debug")) {
                    System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "DEBUG");
                    break argumento;
                }
                if (arg.contains("trace")) {
                    System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "TRACE");
                    break argumento;
                }
                // Con argumentos pero sin especificar nivel de log
                System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "INFO");
            }

        }

        logger = LoggerFactory.getLogger(App.class);
    }

    private void install(List<String> arg) throws IOException {
        databaseConfig();
        appConfig();
        try {
            new Install().run(arg);
        } catch (PersistenceException e) {
            logger.info("Unable to run configuration.");
            logger.error("Database error encountered.");
            logger.debug("Error caused by " + e.toString() + ".");
            logger.debug(ExceptionUtil.getStackTraceAsString(e));
        }
    }

    private void run() throws IOException {
        databaseConfig();
        mailConfig();
        appConfig();
        languageConfig();
        new WebConfig().init();
    }

    private void databaseConfig() throws IOException {
        logger.debug("Starting DatabaseConfig");
        DatabaseConfig.init();
    }

    private void mailConfig() throws IOException {
        logger.debug("Starting MailConfig");
        MailConfig.init();
    }

    private void appConfig() throws IOException {
        logger.debug("Starting AppConfig");
        AppConfig.init();
    }

    private void languageConfig() throws IOException {
        logger.debug("Loading languages");
        LanguageConfig.init();
    }
}
