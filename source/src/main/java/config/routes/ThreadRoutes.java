package config.routes;

import business.exception.InvalidLoginCredentials;
import config.WebConfig;
import model.Message;
import model.Subject;
import model.Thread;
import spark.ModelAndView;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class ThreadRoutes {

    public ThreadRoutes(WebConfig webConfig) {
        // /thread
        get("/thread", (req, res) -> {
            Map<String, Object> map = webConfig.createMap(req);
            map.put("threads", webConfig.getThreadService()
                    .assignedThreads(webConfig.getAuthenticatedUser(req)));
            return new ModelAndView(map, "threadList.ftl");
        }, webConfig.getFreeMarkerEngine());

        before("/thread", (req, res) -> {
            if (webConfig.getAuthenticatedUser(req) == null)
                throw new InvalidLoginCredentials("error.005");
        });

        // /thread/id
        get("/thread/:id", (req, res) -> {
            Map<String, Object> map = webConfig.createMap(req);
            String idString = req.params(":id");
            if (!idString.matches("\\d+")) {
                res.redirect("/thread");
                return null;
            }
            Integer id = Integer.parseInt(idString);

            Thread thread = new Thread().setId(id).one();
            if (thread == null) {
                res.redirect("/thread");
                return null;
            }

            Subject subject = new Subject().setId(thread.getSubjectId()).one();
            List<? extends Message> messages = new Message().setThreadId(thread.getId())
                    .allByThread();
            List<? super Message> replys = new LinkedList<>();
            for (Message message : messages)
                replys.add(message.bindUser());

            Subject[] asuntos = new Subject().all().toArray(new Subject[0]);
            map.put("subjects", asuntos);
            map.put("thread", thread);
            map.put("subject", subject);
            map.put("messages", replys);
            return new ModelAndView(map, "thread.ftl");
        }, webConfig.getFreeMarkerEngine());

        post("/thread/:id/reply", (req, res) -> {

            if (!req.params(":id").matches("\\d+")) {
                res.redirect("/thread/" + req.params(":id"));
                return null;
            }

            Integer id = Integer.parseInt(req.params(":id"));
            String text = req.queryParams("text");

            text = text.replace("\r\n", "<br>");

            webConfig.getThreadService().reply(id, text, webConfig.getAuthenticatedUser(req));

            res.redirect("/thread/" + id);
            return null;
        });

        post("/thread/:id/close", (req, res) -> {
            if (!req.params(":id").matches("\\d+")) {
                res.redirect("/thread/" + req.params(":id"));
                return null;
            }
            Integer id = Integer.parseInt(req.params(":id"));

            webConfig.getThreadService().close(id, webConfig.getAuthenticatedUser(req));

            res.redirect("/thread/" + id);
            return null;
        });

        post("/thread/:id/subject", (req, res) -> {
            if (!req.params(":id").matches("\\d+")) {
                res.redirect("/thread/" + req.params(":id"));
                return null;
            }

            Integer idThread = Integer.parseInt(req.params(":id"));
            Integer idSubject = Integer.parseInt(req.queryParams("subject"));

            webConfig.getThreadService().changeSubject(idThread, idSubject,
                    webConfig.getAuthenticatedUser(req));

            res.redirect("/thread/" + idThread);
            return null;
        });

        before("/thread/*", (req, res) -> {
            if (webConfig.getAuthenticatedUser(req) == null)
                throw new InvalidLoginCredentials("error.005");
        });

    }
}
