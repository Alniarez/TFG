package persistence;

import model.Assignment;

import java.util.List;

public interface AssignmentDAO extends DAO<Assignment> {

    List<? extends Assignment> allByUserId(Assignment assignment);

}
