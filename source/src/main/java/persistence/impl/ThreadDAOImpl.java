package persistence.impl;

import config.DatabaseConfig;
import model.Thread;
import model.User;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import persistence.ThreadDAO;
import persistence.exception.PersistenceException;

import java.util.List;

public class ThreadDAOImpl implements ThreadDAO {

    @Override
    public Object save(Thread pojo) throws PersistenceException {
        String sql = DatabaseConfig.get("thread_insert");
        Sql2o sql2o = DatabaseConfig.getSql2oConnection();
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).bind(pojo).executeUpdate().getKey();
        }
    }

    @Override
    public Integer update(Thread pojo) {
        Integer updated = 0;
        String sql = DatabaseConfig.get("thread_update");
        Sql2o sql2o = DatabaseConfig.getSql2oConnection();
        try (Connection con = sql2o.open()) {
            updated = con.createQuery(sql).bind(pojo).executeUpdate().getResult();
        }
        return updated;
    }

    /*
     * (non-Javadoc)
     *
     * @see model.ActiveRecord#delete()
     *
     * Borra los mensajes del tema y despues el tema en una sola transacción.
     */
    @Override
    public Integer delete(Thread pojo) {
        Integer deleted = 0;
        String thread_delete = DatabaseConfig.get("thread_delete");
        String message_delete_by_thread_id = DatabaseConfig.get("message_delete_by_thread_id");
        Sql2o sql2o = DatabaseConfig.getSql2oConnection();
        try (Connection con = sql2o.beginTransaction()) {

            deleted += con.createQuery(message_delete_by_thread_id).addParameter("id", pojo.getId())
                    .executeUpdate().getResult();
            deleted += con.createQuery(thread_delete).addParameter("id", pojo.getId())
                    .executeUpdate().getResult();
            con.commit();
        }
        return deleted;
    }

    @Override
    public Thread one(Thread pojo) {
        List<? extends Thread> users;
        String sql = DatabaseConfig.get("thread_one");
        Sql2o sql2o = DatabaseConfig.getSql2oConnection();
        try (Connection con = sql2o.open()) {
            users = con.createQuery(sql).bind(pojo).executeAndFetch(pojo.getClass());
        }
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public List<? extends Thread> all(Thread pojo) {
        String sql = DatabaseConfig.get("thread_all");
        Sql2o sql2o = DatabaseConfig.getSql2oConnection();
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(pojo.getClass());
        }
    }

    @Override
    public List<Thread> allAssigned(User pojo) {
        String sql = DatabaseConfig.get("thread_all_assigned");
        Sql2o sql2o = DatabaseConfig.getSql2oConnection();
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).bind(pojo).executeAndFetch(Thread.class);
        }
    }

}
