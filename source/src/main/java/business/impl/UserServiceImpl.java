package business.impl;

import business.UserService;
import business.exception.BusinessException;
import business.exception.InvalidLoginCredentials;
import model.Assignment;
import model.Subject;
import model.User;
import persistence.exception.PersistenceException;
import util.PasswordUtil;

import java.util.List;

public class UserServiceImpl implements UserService {

    @Override
    public User login(String login, String pass) throws InvalidLoginCredentials {
        User user = new User().setLogin(login).oneByLogin();
        if (user == null)
            // No existe o desactivado
            throw new InvalidLoginCredentials("error.003");
        if (!PasswordUtil.verify(pass, user.getPassword()))
            // Contraseña incorrecta
            throw new InvalidLoginCredentials("error.003");
        return user;
    }

    @Override
    public void activate(User user) throws BusinessException {
        update(user.setActive(true));
    }

    @Override
    public void deactivate(User user) throws BusinessException {
        update(user.setActive(false));
    }

    @Override
    public void giveAdminPower(User user) throws BusinessException {
        update(user.setAdmin(true));
    }

    @Override
    public void removeAdminPower(User user) throws BusinessException {
        update(user.setAdmin(false));
    }

    @Override
    public Integer update(User user) throws BusinessException {
        try {
            return user.update();
        } catch (PersistenceException e) {
            throw new BusinessException(e.getMessage());
        }

    }

    @Override
    public Integer save(User user) throws BusinessException {
        try {
            return (Integer) user.save();
        } catch (PersistenceException e) {
            throw new BusinessException(e.getMessage());
        }

    }

    @Override
    public List<? extends Subject> getAssignedSubjects(User user) {
        return user.assignedSubjects();
    }

    @Override
    public List<? extends Subject> getUnassignedSubjects(User user) {
        return user.unassignedSubjects();
    }

    @Override
    public void assign(User user, Integer subject) throws BusinessException {
        try {
            new Assignment().setSubjectId(subject).setUserId(user.getId()).save();
        } catch (PersistenceException e) {
            throw new BusinessException(e.getMessage(), e);
        }

    }

    @Override
    public void unassign(User user, Integer subject) throws BusinessException {
        try {
            new Assignment().setSubjectId(subject).setUserId(user.getId()).delete();
        } catch (PersistenceException e) {
            throw new BusinessException(e.getMessage(), e);
        }

    }

}
