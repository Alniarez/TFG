package config.routes;

import business.exception.InvalidLoginCredentials;
import business.exception.InvalidPermissions;
import config.WebConfig;

import static spark.Spark.*;

public class SubjectRoutes {

    public SubjectRoutes(WebConfig config) {

        post("/subject/newSubject", (req, res) -> {
            String code = req.queryParams("subjectCode");
            config.getThreadService().addSubject(code);

            res.redirect("/subjects");
            return null;
        });

        get("/subject/delete/:id", (req, res) -> {

            String idString = req.params(":id");

            if (idString.matches("\\d+")) {
                Integer id = Integer.parseInt(idString);

                config.getThreadService().deleteSubject(id);
            }

            res.redirect("/subjects");
            return null;
        });

        before("/subject/*", (req, res) -> {
            if (config.getAuthenticatedUser(req) == null)
                throw new InvalidLoginCredentials("error.005");
            if (!config.getAuthenticatedUser(req).isAdmin())
                throw new InvalidPermissions("error.004");
        });

    }

}
