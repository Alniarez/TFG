package config.routes;

import config.WebConfig;
import model.User;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.Map;

import static spark.Spark.*;

/**
 * Routes related to use authentication.
 *
 * @author Jorge
 */
public class AuthenticationRoutes {

    public AuthenticationRoutes(WebConfig webConfig) {
        // /login
        get("/login", (req, res) -> {
            Map<String, Object> map = webConfig.createMap(req);
            return new ModelAndView(map, "login.ftl");
        }, webConfig.getFreeMarkerEngine());

        post("/login", (req, res) -> {
            String loginName = req.queryParams("name"), password = req.queryParams("password");

            webConfig.addAuthenticatedUser(req,
                    webConfig.getUserService().login(loginName, password));

            res.redirect("/thread");
            return null;
        }, new FreeMarkerEngine());

        before("/login", (req, res) -> {
            User authUser = webConfig.getAuthenticatedUser(req);
            if (authUser != null) {
                res.redirect("/thread");
                halt();
            }
        });

        // /logout
        get("/logout", (req, res) -> {
            webConfig.removeAuthenticatedUser(req);
            res.redirect("/login");
            return null;
        });

    }

}
