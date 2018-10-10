package persistence;

public interface PersistenceFactory {

    AssignmentDAO getAssignmentDAO();

    MessageDAO getMessageDAO();

    SubjectDAO getSubjectDAO();

    ThreadDAO getThreadDAO();

    UserDAO getUserDAO();

}
