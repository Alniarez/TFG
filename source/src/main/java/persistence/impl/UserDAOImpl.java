package persistence.impl;

import config.DatabaseConfig;
import model.Thread;
import model.User;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;
import persistence.UserDAO;
import persistence.exception.PersistenceException;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    @Override
    public Object save(User pojo) throws PersistenceException {
        String sql = DatabaseConfig.get("user_insert");
        Sql2o sql2o = DatabaseConfig.getSql2oConnection();
        Connection con;
        try {
            con = sql2o.open();
            return con.createQuery(sql).bind(pojo).executeUpdate().getKey();
        } catch (Sql2oException e) {
            try {
                throw (SQLException) e.getCause();
            } catch (SQLIntegrityConstraintViolationException e1) {
                throw new PersistenceException("error.002", e1);
            } catch (SQLException e1) {
                throw new RuntimeException("Database error occurred", e1);
            }
        }
    }

    @Override
    public Integer update(User pojo) throws PersistenceException {
        Integer updated = 0;
        String sql = DatabaseConfig.get("user_update");
        Sql2o sql2o = DatabaseConfig.getSql2oConnection();
        Connection con;
        try {
            con = sql2o.open();
            updated = con.createQuery(sql).bind(pojo).executeUpdate().getResult();
        } catch (Sql2oException e) {
            try {
                throw (SQLException) e.getCause();
            } catch (SQLIntegrityConstraintViolationException e1) {
                throw new PersistenceException("error.002", e1);
            } catch (SQLException e1) {
                throw new RuntimeException("Database error occurred", e1);
            }
        }
        return updated;
    }

    @Override
    public Integer delete(User pojo) {
        Integer deletedId = 0;
        String sql = DatabaseConfig.get("user_delete");
        Sql2o sql2o = DatabaseConfig.getSql2oConnection();
        try (Connection con = sql2o.open()) {
            deletedId = con.createQuery(sql).bind(pojo).executeUpdate().getResult();
        }
        return deletedId;
    }

    @Override
    public User one(User pojo) {
        List<? extends User> users;
        String sql = DatabaseConfig.get("user_one");
        Sql2o sql2o = DatabaseConfig.getSql2oConnection();
        try (Connection con = sql2o.open()) {
            users = con.createQuery(sql).bind(pojo).executeAndFetch(pojo.getClass());
        }
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public List<? extends User> all(User pojo) {
        String sql = DatabaseConfig.get("user_all");
        Sql2o sql2o = DatabaseConfig.getSql2oConnection();
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(pojo.getClass());
        }
    }

    @Override
    public User oneByLogin(User pojo) {
        List<? extends User> users;
        String sql = DatabaseConfig.get("user_one_by_login");
        Sql2o sql2o = DatabaseConfig.getSql2oConnection();
        try (Connection con = sql2o.open()) {
            users = con.createQuery(sql).bind(pojo).executeAndFetch(pojo.getClass());
        }
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public List<? extends User> allAssignedTo(Thread pojo) {
        String sql = DatabaseConfig.get("user_all_assigned_to_thread");
        Sql2o sql2o = DatabaseConfig.getSql2oConnection();
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).bind(pojo).executeAndFetch(User.class);
        }
    }
}
