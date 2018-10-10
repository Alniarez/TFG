package config.routes;

import business.exception.BusinessException;
import business.exception.InvalidPermissions;
import config.WebConfig;
import model.Subject;
import model.User;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import util.PasswordUtil;

import java.util.List;
import java.util.Map;

import static config.LanguageConfig.getLanguage;
import static spark.Spark.*;

/**
 * Created by Jorge.
 */
public class ProfileEditRoutes {

    public ProfileEditRoutes(WebConfig config) {
        get("/profile/edit/:id", (req, res) -> {
            Map<String, Object> map = config.createMap(req);
            User user = assertCorrectID(req, res, config);

            map.put("user", user);

            List<? extends Subject> asig = config.getUserService().getAssignedSubjects(user);
            if (!asig.isEmpty())
                map.put("assignedSubjects", asig);

            List<? extends Subject> unasig = config.getUserService().getUnassignedSubjects(user);
            if (!unasig.isEmpty())
                map.put("unassignedSubjects", unasig);

            return new ModelAndView(map, "profileEdit.ftl");
        }, config.getFreeMarkerEngine());

        post("/profile/edit/:id", (req, res) -> {
            User user = assertCorrectID(req, res, config);
            String name = req.queryParams("name"), email = req.queryParams("email"),
                    login = req.queryParams("login");

            config.getUserService().update(user.setName(name).setEmail(email).setLogin(login));

            // res.cookie("success",
            // getLanguage(config.getLocale(req)).get("exitoCambioDatos"), 2);
            config.setMessage(req, "success",
                    getLanguage(config.getLocale(req)).get("exitoCambioDatos"));

            res.redirect("/profile/edit/" + req.params(":id"));
            return null;
        });

        post("/profile/edit/:id/pass", (req, res) -> {
            User user = assertCorrectID(req, res, config);
            String new1 = req.queryParams("new1"), new2 = req.queryParams("new2");

            if (new1.equals(new2)) {

                config.getUserService().update(user.setPassword(PasswordUtil.hash(new1)));

                // res.cookie("success",
                // getLanguage(config.getLocale(req)).get("exitoCambioClave"),
                // 2);
                config.setMessage(req, "success",
                        getLanguage(config.getLocale(req)).get("exitoCambioClave"));

            } else {
                // res.cookie("error",
                // getLanguage(config.getLocale(req)).get("clavesNoCoinciden"),
                // 2);
                config.setMessage(req, "error",
                        getLanguage(config.getLocale(req)).get("clavesNoCoinciden"));
            }

            res.redirect("/profile/edit/" + req.params(":id"));
            return null;
        });

        // Activar usuario
        post("/profile/edit/:id/activate", (req, res) -> {
            User user = assertCorrectID(req, res, config);
            assertUserIsAdmin(req, config);

            config.getUserService().activate(user);

            // res.cookie("success",
            // getLanguage(config.getLocale(req)).get("exitoActivo"), 2);
            config.setMessage(req, "success",
                    getLanguage(config.getLocale(req)).get("exitoActivo"));

            res.redirect("/profile/edit/" + req.params(":id"));
            return null;
        });

        // Desactivar usuario
        post("/profile/edit/:id/deactivate", (req, res) -> {
            User user = assertCorrectID(req, res, config);

            config.getUserService().deactivate(user);

            // res.cookie("success",
            // getLanguage(config.getLocale(req)).get("exitoNoActivo"), 2);
            config.setMessage(req, "success",
                    getLanguage(config.getLocale(req)).get("exitoNoActivo"));

            res.redirect("/profile/edit/" + req.params(":id"));
            return null;
        });

        // Dar privilegios de administración
        post("/profile/edit/:id/admin", (req, res) -> {
            User user = assertCorrectID(req, res, config);
            assertUserIsAdmin(req, config);

            config.getUserService().giveAdminPower(user);

            // res.cookie("success",
            // getLanguage(config.getLocale(req)).get("exitoAdmin"), 2);
            config.setMessage(req, "success", getLanguage(config.getLocale(req)).get("exitoAdmin"));

            res.redirect("/profile/edit/" + req.params(":id"));
            return null;
        });

        // Quitar privilegios de administración
        post("/profile/edit/:id/noadmin", (req, res) -> {
            User user = assertCorrectID(req, res, config);
            assertUserIsAdmin(req, config);

            config.getUserService().removeAdminPower(user);

            // res.cookie("success",
            // getLanguage(config.getLocale(req)).get("exitoNoAdmin"), 2);
            config.setMessage(req, "success",
                    getLanguage(config.getLocale(req)).get("exitoNoAdmin"));

            res.redirect("/profile/edit/" + req.params(":id"));
            return null;
        });

        // Asignar temática
        post("/profile/edit/:id/asign", (req, res) -> {
            User user = assertCorrectID(req, res, config);
            assertUserIsAdmin(req, config);

            Integer subject = Integer.parseInt(req.queryParams("subject"));

            config.getUserService().assign(user, subject);

            config.setMessage(req, "success",
                    getLanguage(config.getLocale(req)).get("exitoAsignar"));

            res.redirect("/profile/edit/" + req.params(":id"));
            return null;
        });

        // quitar asignación
        post("/profile/edit/:id/unasign", (req, res) -> {
            User user = assertCorrectID(req, res, config);
            assertUserIsAdmin(req, config);

            Integer subject = Integer.parseInt(req.queryParams("subject"));

            config.getUserService().unassign(user, subject);

            config.setMessage(req, "success",
                    getLanguage(config.getLocale(req)).get("exitoQuitarAsignar"));

            res.redirect("/profile/edit/" + req.params(":id"));
            return null;
        });

        get("/newUser", (req, res) -> {
            Map<String, Object> map = config.createMap(req);
            return new ModelAndView(map, "profileNew.ftl");
        }, config.getFreeMarkerEngine());

        post("/newUser", (req, res) -> {
            String name = req.queryParams("name"), login = req.queryParams("login"),
                    email = req.queryParams("email"), pass1 = req.queryParams("new1"),
                    pass2 = req.queryParams("new2");

            if (!pass1.equals(pass2))
                throw new BusinessException("error.007");

            Integer newID = config.getUserService().save(new User().setAdmin(false).setName(name)
                    .setEmail(email).setLogin(login).setPassword(PasswordUtil.hash(pass1)));

            res.redirect("/profile/" + newID);
            return null;
        });

        before("/newUser", (req, res) -> {
            if (config.getAuthenticatedUser(req) == null)
                throw new BusinessException("error.005");
            assertUserIsAdmin(req, config);
        });
    }

    private User assertCorrectID(Request req, Response res, WebConfig config)
            throws InvalidPermissions, BusinessException {
        String idString = req.params(":id");
        User user = null;

        if (!idString.matches("\\d+"))
            throw new BusinessException("error.006");

        Integer id = Integer.parseInt(idString);
        user = new User().setId(id).one();

        if (user == null)
            throw new BusinessException("error.006");

        if (!(config.getAuthenticatedUser(req).getId().equals(id))
                && !config.getAuthenticatedUser(req).isAdmin())
            throw new InvalidPermissions("error.004");

        return user;
    }

    private void assertUserIsAdmin(Request req, WebConfig config) throws InvalidPermissions {
        if (!config.getAuthenticatedUser(req).isAdmin())
            throw new InvalidPermissions("error.004");
    }
}
