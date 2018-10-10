package persistence;

import model.Subject;
import model.User;

import java.util.List;

public interface SubjectDAO extends DAO<Subject> {

    List<? extends Subject> allAssigned(User user);

    List<? extends Subject> allUnassigned(User user);

}
