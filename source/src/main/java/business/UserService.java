package business;

import business.exception.BusinessException;
import business.exception.InvalidLoginCredentials;
import model.Subject;
import model.User;

import java.util.List;

public interface UserService {

    User login(String user, String pass) throws InvalidLoginCredentials;

    void activate(User user) throws BusinessException;

    void deactivate(User user) throws BusinessException;

    void giveAdminPower(User user) throws BusinessException;

    void removeAdminPower(User user) throws BusinessException;

    Integer update(User user) throws BusinessException;

    Integer save(User user) throws BusinessException;

    List<? extends Subject> getAssignedSubjects(User user);

    List<? extends Subject> getUnassignedSubjects(User user);

    void assign(User user, Integer subject) throws BusinessException;

    void unassign(User user, Integer subject) throws BusinessException;
}
