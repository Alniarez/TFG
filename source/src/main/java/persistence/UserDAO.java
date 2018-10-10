package persistence;

import model.Thread;
import model.User;

import java.util.List;

public interface UserDAO extends DAO<User> {

    User oneByLogin(User pojo);

    List<? extends User> allAssignedTo(Thread thread);

}
