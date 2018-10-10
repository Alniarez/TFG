package config.routes;

import config.LanguageConfig;
import config.WebConfig;

import static spark.Spark.get;
import static spark.Spark.post;

public class LanguageRoutes {

    public LanguageRoutes(WebConfig config) {

        get("/language/:locale", (req, res) -> {
            String locale = req.params(":locale");
            if (LanguageConfig.getLocales().contains(locale))
                config.setLocale(req, locale);
            res.redirect("/" + config.getLastURI(req));
            return null;
        });

        post("/language/:locale", (req, res) -> {
            String locale = req.params(":locale");
            if (LanguageConfig.getLocales().contains(locale))
                config.setLocale(req, locale);
            res.redirect("/" + config.getLastURI(req));
            return null;
        });
    }

}
