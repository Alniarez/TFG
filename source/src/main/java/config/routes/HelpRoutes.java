package config.routes;

import config.WebConfig;
import model.Subject;
import spark.ModelAndView;

import java.util.Map;

import static config.LanguageConfig.getLanguage;
import static spark.Spark.get;
import static spark.Spark.post;

public class HelpRoutes {

    public HelpRoutes(WebConfig webConfig) {
        // /help
        get("/help", (req, res) -> {
            Map<String, Object> map = webConfig.createMap(req);
            Subject[] asuntos = new Subject().all().toArray(new Subject[0]);
            map.put("subjects", asuntos);
            return new ModelAndView(map, "help.ftl");
        }, webConfig.getFreeMarkerEngine());

        post("/help", (req, res) -> {
            String name = req.queryParams("name"), email = req.queryParams("email"),
                    subject = req.queryParams("subject"), topic = req.queryParams("topic"),
                    text = req.queryParams("text");

            text = text.replace("\r\n", "<br>");

            webConfig.getThreadService().create(name, email, subject, topic, text);

            // res.cookie("info",
            // getLanguage(webConfig.getLocale(req)).get("enviadoExito"), 2);
            webConfig.setMessage(req, "info",
                    getLanguage(webConfig.getLocale(req)).get("enviadoExito"));

            res.redirect("/message");
            return null;
        });

    }

}
