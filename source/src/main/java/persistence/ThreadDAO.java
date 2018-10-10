package persistence;

import model.Thread;
import model.User;

import java.util.List;

public interface ThreadDAO extends DAO<Thread> {

    List<Thread> allAssigned(User user);

}
