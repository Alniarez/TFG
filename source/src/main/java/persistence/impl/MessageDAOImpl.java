package persistence.impl;

import config.DatabaseConfig;
import model.Message;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import persistence.MessageDAO;

import java.util.List;

public class MessageDAOImpl implements MessageDAO {

    @Override
    public Object save(Message pojo) {
        String sql = DatabaseConfig.get("message_insert");
        Sql2o sql2o = DatabaseConfig.getSql2oConnection();
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).bind(pojo).executeUpdate().getKey();
        }
    }

    @Override
    public Integer update(Message pojo) {
        Integer updated = 0;
        String sql = DatabaseConfig.get("message_update");
        Sql2o sql2o = DatabaseConfig.getSql2oConnection();
        try (Connection con = sql2o.open()) {
            updated = con.createQuery(sql).bind(pojo).executeUpdate().getResult();
        }
        return updated;
    }

    @Override
    public Integer delete(Message pojo) {
        Integer deletedId = 0;
        String sql = DatabaseConfig.get("message_delete");
        Sql2o sql2o = DatabaseConfig.getSql2oConnection();
        try (Connection con = sql2o.open()) {
            deletedId = (Integer) con.createQuery(sql).bind(pojo).executeUpdate().getResult();
        }
        return deletedId;
    }

    @Override
    public Message one(Message pojo) {
        List<? extends Message> users;
        String sql = DatabaseConfig.get("message_one");
        Sql2o sql2o = DatabaseConfig.getSql2oConnection();
        try (Connection con = sql2o.open()) {
            users = con.createQuery(sql).bind(pojo).executeAndFetch(pojo.getClass());
        }
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public List<? extends Message> all(Message pojo) {
        String sql = DatabaseConfig.get("message_all");
        Sql2o sql2o = DatabaseConfig.getSql2oConnection();
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(pojo.getClass());
        }
    }

    @Override
    public List<? extends Message> allByThread(Message pojo) {
        String sql = DatabaseConfig.get("message_all_by_thread");
        Sql2o sql2o = DatabaseConfig.getSql2oConnection();
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).bind(pojo).executeAndFetch(pojo.getClass());
        }
    }

}
