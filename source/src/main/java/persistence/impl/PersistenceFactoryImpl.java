package persistence.impl;

import persistence.*;

public class PersistenceFactoryImpl implements PersistenceFactory {

    @Override
    public AssignmentDAO getAssignmentDAO() {
        return new AssignmentDAOImpl();
    }

    @Override
    public MessageDAO getMessageDAO() {
        return new MessageDAOImpl();
    }

    @Override
    public SubjectDAO getSubjectDAO() {
        return new SubjectDAOImp();
    }

    @Override
    public ThreadDAO getThreadDAO() {
        return new ThreadDAOImpl();
    }

    @Override
    public UserDAO getUserDAO() {
        return new UserDAOImpl();
    }

}
