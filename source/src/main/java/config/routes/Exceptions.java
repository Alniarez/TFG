package config.routes;

import business.exception.BusinessException;
import business.exception.InvalidLoginCredentials;
import business.exception.InvalidPermissions;
import config.WebConfig;
import persistence.exception.PersistenceException;

import static config.LanguageConfig.getLanguage;
import static spark.Spark.exception;

public class Exceptions {

    public Exceptions(WebConfig config) {

        exception(InvalidLoginCredentials.class, (exception, request, response) -> {
            // response.cookie("error",
            // getLanguage(webConfig.getLocale(request)).get(exception.getMessage()),
            // 2);
            config.setMessage(request, "error",
                    getLanguage(config.getLocale(request)).get(exception.getMessage()));
            response.redirect("/login");
        });

        exception(InvalidPermissions.class, (exception, request, response) -> {
            // response.cookie("error",
            // getLanguage(webConfig.getLocale(request)).get(exception.getMessage()),
            // 2);
            config.setMessage(request, "error",
                    getLanguage(config.getLocale(request)).get(exception.getMessage()));
            response.redirect("/message");
        });

        exception(PersistenceException.class, (exception, request, response) -> {
            // response.cookie("error",
            // getLanguage(webConfig.getLocale(request)).get("errorBaseDatos"),
            // 2);
            config.setMessage(request, "error",
                    getLanguage(config.getLocale(request)).get("errorBaseDatos"));

            response.redirect("/message");
        });

        exception(BusinessException.class, (exception, request, response) -> {
            // response.cookie("error",
            // getLanguage(webConfig.getLocale(request)).get(exception.getMessage()),
            // 2);
            config.setMessage(request, "error",
                    getLanguage(config.getLocale(request)).get(exception.getMessage()));
            response.redirect("/" + config.getLastURI(request));

        });

    }

}
