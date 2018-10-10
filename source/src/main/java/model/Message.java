package model;

import common.Factory;
import persistence.exception.PersistenceException;

import java.util.Date;
import java.util.List;

public class Message implements ActiveRecord<Message> {

    private Integer id, userId, threadId;
    private String text;
    private Date date;

    private User user;

    public Message() {
    }

    public Message(Integer id, Integer userId, Integer threadId, String text, Date date) {
        super();
        this.id = id;
        this.userId = userId;
        this.threadId = threadId;
        this.text = text;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public Message setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getUserId() {
        return userId;
    }

    public Message setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public Integer getThreadId() {
        return threadId;
    }

    public Message setThreadId(Integer threadId) {
        this.threadId = threadId;
        return this;
    }

    public String getText() {
        return text;
    }

    public Message setText(String text) {
        this.text = text;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Message bindUser() {
        user = new User().setId(userId).one();
        return this;
    }

    public Date getDate() {
        return date;
    }

    public Message setDate(Date date) {
        this.date = date;
        return this;
    }

    @Override
    public Object save() throws PersistenceException {
        return Factory.persistence.getMessageDAO().save(this);
    }

    @Override
    public Integer update() throws PersistenceException {
        return Factory.persistence.getMessageDAO().update(this);
    }

    @Override
    public Integer delete() throws PersistenceException {
        return Factory.persistence.getMessageDAO().delete(this);
    }

    @Override
    public Message one() {
        return Factory.persistence.getMessageDAO().one(this);
    }

    @Override
    public List<? extends Message> all() {
        return Factory.persistence.getMessageDAO().all(this);
    }

    public List<? extends Message> allByThread() {
        return Factory.persistence.getMessageDAO().allByThread(this);
    }
}
