package model;

import common.Factory;
import persistence.exception.PersistenceException;
import util.GravatarUtil;

import java.util.Date;
import java.util.List;

public class Thread implements ActiveRecord<Thread> {

    private Integer id, subjectId;
    private String name, email, text, topic;
    private Date date;
    private boolean open = true;

    public Thread() {
    }

    public boolean isOpen() {
        return open;
    }

    public Thread setOpen(boolean open) {
        this.open = open;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public Thread setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Thread setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public Thread setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Thread setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getText() {
        return text;
    }

    public Thread setText(String text) {
        this.text = text;
        return this;
    }

    public String getTopic() {
        return topic;
    }

    public Thread setTopic(String topic) {
        this.topic = topic;
        return this;
    }

    public String getGravatar() {
        return GravatarUtil.gravatarURL(email.toLowerCase());
    }

    public Date getDate() {
        return date;
    }

    public Thread setDate(Date date) {
        this.date = date;
        return this;
    }

    @Override
    public String toString() {
        return "Thread [id=" + id + ", subjectId=" + subjectId + ", name=" + name + ", email="
                + email + ", open=" + open + ", text=" + text + ", topic=" + topic + ", date="
                + date + "]";
    }

    @Override
    public Object save() throws PersistenceException {
        return Factory.persistence.getThreadDAO().save(this);
    }

    @Override
    public Integer update() throws PersistenceException {
        return Factory.persistence.getThreadDAO().update(this);
    }

    @Override
    public Integer delete() throws PersistenceException {
        return Factory.persistence.getThreadDAO().delete(this);
    }

    @Override
    public Thread one() {
        return Factory.persistence.getThreadDAO().one(this);
    }

    @Override
    public List<? extends Thread> all() {
        return Factory.persistence.getThreadDAO().all(this);
    }

    public List<? extends User> assignedUsers() {
        return Factory.persistence.getUserDAO().allAssignedTo(this);
    }

}
