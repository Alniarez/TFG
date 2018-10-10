package persistence.impl;

import config.DatabaseConfig;
import model.Subject;
import model.User;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;
import persistence.SubjectDAO;
import persistence.exception.PersistenceException;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public class SubjectDAOImp implements SubjectDAO {

    @Override
    public Object save(Subject pojo) throws PersistenceException {
        String sql = DatabaseConfig.get("subject_insert");
        Sql2o sql2o = DatabaseConfig.getSql2oConnection();
        Connection con;
        try {
            con = sql2o.open();
            return con.createQuery(sql).bind(pojo).executeUpdate().getKey();
        } catch (Sql2oException e) {
            try {
                throw (SQLException) e.getCause();
            } catch (SQLIntegrityConstraintViolationException e1) {
                throw new PersistenceException("error.001", e1);
            } catch (SQLException e1) {
                throw new RuntimeException("Database error occurred.", e1);
            }
        }
    }

    @Override
    public Integer update(Subject pojo) throws PersistenceException {
        Integer updatedId = 0;
        String sql = DatabaseConfig.get("subject_update");
        Sql2o sql2o = DatabaseConfig.getSql2oConnection();
        Connection con;
        try {
            con = sql2o.open();
            updatedId = (Integer) con.createQuery(sql).bind(pojo).executeUpdate().getResult();
        } catch (Sql2oException e) {
            try {
                throw (SQLException) e.getCause();
            } catch (SQLIntegrityConstraintViolationException e1) {
                throw new PersistenceException("error.001", e1);
            } catch (SQLException e1) {
                throw new RuntimeException("Database error occurred", e1);
            }
        }
        return updatedId;
    }

    @Override
    public Integer delete(Subject pojo) throws PersistenceException {
        Integer deleted = 0;
        String sql = DatabaseConfig.get("subject_delete");
        Sql2o sql2o = DatabaseConfig.getSql2oConnection();
        try {
            Connection con = sql2o.open();
            deleted = con.createQuery(sql).bind(pojo).executeUpdate().getResult();
        } catch (Sql2oException e) {
            try {
                throw (SQLException) e.getCause();
            } catch (SQLIntegrityConstraintViolationException e1) {
                throw new PersistenceException("error.008");
            } catch (SQLException e1) {
                throw new RuntimeException("Database error occurred", e1);
            }
        }
        return deleted;
    }

    @Override
    public Subject one(Subject pojo) {
        List<? extends Subject> subjects;
        String sql = DatabaseConfig.get("subject_one");
        Sql2o sql2o = DatabaseConfig.getSql2oConnection();
        try (Connection con = sql2o.open()) {
            subjects = con.createQuery(sql).bind(pojo).executeAndFetch(pojo.getClass());
        }
        return subjects.isEmpty() ? null : subjects.get(0);
    }

    @Override
    public List<? extends Subject> all(Subject pojo) {
        String sql = DatabaseConfig.get("subject_all");
        Sql2o sql2o = DatabaseConfig.getSql2oConnection();
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(pojo.getClass());
        }
    }

    @Override
    public List<? extends Subject> allAssigned(User pojo) {
        String sql = DatabaseConfig.get("subject_all_assigned");
        Sql2o sql2o = DatabaseConfig.getSql2oConnection();
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).bind(pojo).executeAndFetch(Subject.class);
        }
    }

    @Override
    public List<? extends Subject> allUnassigned(User pojo) {
        String sql = DatabaseConfig.get("subject_all_unassigned");
        Sql2o sql2o = DatabaseConfig.getSql2oConnection();
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).bind(pojo).executeAndFetch(Subject.class);
        }
    }
}
