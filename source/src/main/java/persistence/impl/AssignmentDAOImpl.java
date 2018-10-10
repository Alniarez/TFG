package persistence.impl;

import config.DatabaseConfig;
import model.Assignment;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;
import persistence.AssignmentDAO;
import persistence.exception.PersistenceException;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public class AssignmentDAOImpl implements AssignmentDAO {

    @Override
    public Object save(Assignment pojo) throws PersistenceException {
        String sql = DatabaseConfig.get("assignment_insert");
        Sql2o sql2o = DatabaseConfig.getSql2oConnection();
        Connection con;
        try {
            con = sql2o.open();
            return con.createQuery(sql).bind(pojo).executeUpdate().getKey();
        } catch (Sql2oException e) {
            try {
                throw (SQLException) e.getCause();
            } catch (SQLIntegrityConstraintViolationException e1) {
                throw new PersistenceException("error.009", e1);
            } catch (SQLException e1) {
                throw new RuntimeException("Database error occurred", e1);
            }
        }
    }

    @Override
    public Integer update(Assignment pojo) {
        Integer updated = 0;
        String sql = DatabaseConfig.get("assignment_update");
        Sql2o sql2o = DatabaseConfig.getSql2oConnection();
        try (Connection con = sql2o.open()) {
            updated = con.createQuery(sql).bind(pojo).executeUpdate().getResult();
        }
        return updated;
    }

    @Override
    public Integer delete(Assignment pojo) {
        Integer deletedId = 0;
        String sql = DatabaseConfig.get("assignment_delete");
        Sql2o sql2o = DatabaseConfig.getSql2oConnection();
        try (Connection con = sql2o.open()) {
            deletedId = con.createQuery(sql).bind(pojo).executeUpdate().getResult();
        }
        return deletedId;
    }

    @Override
    public Assignment one(Assignment pojo) {
        List<? extends Assignment> users;
        String sql = DatabaseConfig.get("assignment_one");
        Sql2o sql2o = DatabaseConfig.getSql2oConnection();
        try (Connection con = sql2o.open()) {
            users = con.createQuery(sql).bind(pojo).executeAndFetch(pojo.getClass());
        }
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public List<? extends Assignment> all(Assignment pojo) {
        String sql = DatabaseConfig.get("assignment_all");
        Sql2o sql2o = DatabaseConfig.getSql2oConnection();
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(pojo.getClass());
        }
    }

    @Override
    public List<? extends Assignment> allByUserId(Assignment pojo) {
        String sql = DatabaseConfig.get("assignment_all_by_user_id");
        Sql2o sql2o = DatabaseConfig.getSql2oConnection();
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).bind(pojo).executeAndFetch(pojo.getClass());
        }
    }

}
