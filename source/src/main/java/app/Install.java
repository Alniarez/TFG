package app;

import config.AppConfig;
import model.Subject;
import model.User;
import persistence.exception.PersistenceException;
import util.PasswordUtil;

import java.util.List;

public class Install {

    public void run(List<String> arg) throws PersistenceException {
        if (arg.contains("soloAdmin"))
            createAdminUser();
        else {
            createAdminUser();
            createSubjects();
        }
    }

    private void createAdminUser() throws PersistenceException {
        new User().setName(AppConfig.get("name")).setEmail(AppConfig.get("email")).setAdmin(true)
                .setLogin(AppConfig.get("login"))
                .setPassword(PasswordUtil.hash(AppConfig.get("password"))).save();
    }

    private void createSubjects() throws PersistenceException {
        String[] subjects = AppConfig.get("subjects").split(AppConfig.get("subjectSeparator"));
        for (String subject : subjects) {
            new Subject().setCode(subject).save();
        }

    }

}
