package config;

import static config.LanguageConfig.getLanguage;
import static config.LanguageConfig.getLanguages;
import static spark.Spark.after;
import static spark.Spark.get;
import static spark.Spark.halt;
import static spark.Spark.port;

import java.util.HashMap;
import java.util.Map;

import business.ThreadService;
import business.UserService;
import common.Factory;
import config.routes.AdminRoutes;
import config.routes.AuthenticationRoutes;
import config.routes.Exceptions;
import config.routes.HelpRoutes;
import config.routes.LanguageRoutes;
import config.routes.ProfileEditRoutes;
import config.routes.ProfileRoutes;
import config.routes.SubjectRoutes;
import config.routes.ThreadRoutes;
import model.User;
import spark.ModelAndView;
import spark.Request;
import spark.template.freemarker.FreeMarkerEngine;

public class WebConfig{

    private static final String USER_SESSION = "user";
    private static final String LOCALE_SESSION = "locale";
    private static final String LAST_URI = "lastUri";

    private FreeMarkerEngine freeMarkerEngine;

    private UserService userService;
    private ThreadService threadService;

    public void init() {
        port(Integer.parseInt(AppConfig.get("port")));
        setupServices();
        setupFreemarker();
        setupRoutes();
    }

    private void setupServices() {
        userService = Factory.sercive.getUserService();
        threadService = Factory.sercive.getThreadService();
    }

    private void setupFreemarker() {
        freeMarkerEngine = new FreeMarkerEngine();
    }

    private void setupRoutes() {

        new AuthenticationRoutes(this);
        new ThreadRoutes(this);
        new ProfileEditRoutes(this);
        new ProfileRoutes(this);
        new HelpRoutes(this);
        new AdminRoutes(this);
        new SubjectRoutes(this);
        new LanguageRoutes(this);
        new Exceptions(this);

        // /
        get("/", (req, res) -> {
            if (getAuthenticatedUser(req) == null)
                res.redirect("/help");
            else
                res.redirect("/thread");
            return null;
        });

        get("/message", (req, res) -> {
            Map<String, Object> map = createMap(req);
            return new ModelAndView(map, "showMessage.ftl");
        }, getFreeMarkerEngine());

        // *
        get("/*", (req, res) -> {
            halt(404, "URI inválida.");
            return null;
        });

        after(":uri", (req, res) -> {
            String uri = req.params(":uri");
            if (uri.contains("language"))
                return;
            if (uri.contains("delete"))
                return;
            req.session().attribute(LAST_URI, uri);
        });
    }

    public String getLastURI(Request request) {
        return request.session().attribute(LAST_URI);
    }

    public FreeMarkerEngine getFreeMarkerEngine() {
        return freeMarkerEngine;
    }

    public UserService getUserService() {
        return userService;
    }

    public ThreadService getThreadService() {
        return threadService;
    }

    public void removeAuthenticatedUser(Request request) {
        request.session().removeAttribute(USER_SESSION);
    }

    public void addAuthenticatedUser(Request req, User user) {
        req.session().attribute(USER_SESSION, user);
    }

    public User getAuthenticatedUser(Request request) {
        return request.session().attribute(USER_SESSION);
    }

    public void setLocale(Request request, String locale) {
        request.session().attribute(LOCALE_SESSION, locale);
    }

    public String getLocale(Request request) {
        String locale = request.session().attribute(LOCALE_SESSION);
        return (locale == null) ? LanguageConfig.getDefaultLocale() : locale;
    }

    public Map<String, Object> createMap(Request req) {
        Map<String, Object> map = new HashMap<>();
        if (getAuthenticatedUser(req) != null)
            map.put("AuthenticatedUser", getAuthenticatedUser(req));

        String locale = getLocale(req);
        map.put("locale", locale);
        map.put("languages", getLanguages());
        map.put("lang", getLanguage(locale));

        // map.put("error", req.cookie("error"));
        // map.put("warning", req.cookie("warning"));
        // map.put("success", req.cookie("success"));
        // map.put("info", req.cookie("info"));

        map.put("error", getMessage(req, "error"));
        map.put("warning", getMessage(req, "warning"));
        map.put("success", getMessage(req, "success"));
        map.put("info", getMessage(req, "info"));
        return map;
    }

    public void setMessage(Request req, String type, String msg) {
        req.session().attribute(type, msg);
    }

    public String getMessage(Request req, String type) {
        String msg = req.session().attribute(type);
        req.session().removeAttribute(type);
        return msg;
    }

}
