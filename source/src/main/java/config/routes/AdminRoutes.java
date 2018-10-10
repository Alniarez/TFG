package config.routes;

import business.exception.InvalidLoginCredentials;
import business.exception.InvalidPermissions;
import config.WebConfig;
import model.Subject;
import model.User;
import spark.ModelAndView;
import spark.Request;

import java.util.Map;

import static spark.Spark.before;
import static spark.Spark.get;

public class AdminRoutes {

    public AdminRoutes(WebConfig config) {
        // /admin
        get("/admin", (req, res) -> {
            Map<String, Object> map = config.createMap(req);
            return new ModelAndView(map, "admin.ftl");
        }, config.getFreeMarkerEngine());

        get("/subjects", (req, res) -> {
            Map<String, Object> map = config.createMap(req);
            map.put("allSubjects", new Subject().all());
            return new ModelAndView(map, "adminSubjects.ftl");
        }, config.getFreeMarkerEngine());

        get("/users", (req, res) -> {
            Map<String, Object> map = config.createMap(req);
            map.put("allUsers", new User().all());
            return new ModelAndView(map, "adminUsers.ftl");
        }, config.getFreeMarkerEngine());

        before("/admin/*", (req, res) -> {
            assterAdmin(req, config);
        });

        before("/admin", (req, res) -> {
            assterAdmin(req, config);
        });

        before("/users", (req, res) -> {
            assterAdmin(req, config);
        });

        before("/subjects", (req, res) -> {
            assterAdmin(req, config);
        });
    }

    private void assterAdmin(Request req, WebConfig config)
            throws InvalidLoginCredentials, InvalidPermissions {
        if (config.getAuthenticatedUser(req) == null)
            throw new InvalidLoginCredentials("error.005");
        if (!config.getAuthenticatedUser(req).isAdmin())
            throw new InvalidPermissions("error.004");
    }

}
