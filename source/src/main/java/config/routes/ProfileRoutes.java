package config.routes;

import business.exception.BusinessException;
import business.exception.InvalidLoginCredentials;
import config.WebConfig;
import model.Subject;
import model.User;
import spark.ModelAndView;

import java.util.List;
import java.util.Map;

import static spark.Spark.before;
import static spark.Spark.get;

public class ProfileRoutes {

    public ProfileRoutes(WebConfig config) {
        // /profile
        get("/profile/:id", (req, res) -> {
            Map<String, Object> map = config.createMap(req);
            String idString = req.params(":id");

            showProfile: {
                if (!idString.matches("\\d+"))
                    break showProfile;

                Integer id = Integer.parseInt(idString);
                User user = new User().setId(id).one();

                if (user == null)
                    break showProfile;

                map.put("user", user);

                if (config.getAuthenticatedUser(req).isAdmin()
                        || user.getId().equals(config.getAuthenticatedUser(req).getId())) {
                    List<? extends Subject> asig = config.getUserService()
                            .getAssignedSubjects(user);
                    if (!asig.isEmpty())
                        map.put("assignedSubjects", asig);
                }
                return new ModelAndView(map, "profile.ftl");
            }

            throw new BusinessException("error.006");
        }, config.getFreeMarkerEngine());

        before("/profile/*", (req, res) -> {
            if (config.getAuthenticatedUser(req) == null)
                throw new InvalidLoginCredentials("error.005");
        });

        before("/profile", (req, res) -> {
            if (config.getAuthenticatedUser(req) == null)
                throw new InvalidLoginCredentials("error.005");
            res.redirect("/profile/" + config.getAuthenticatedUser(req).getId());
        });
    }
}
