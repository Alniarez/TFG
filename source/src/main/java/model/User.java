package model;

import common.Factory;
import persistence.exception.PersistenceException;
import util.GravatarUtil;

import java.util.List;

/**
 * @author Jorge
 */
public class User implements ActiveRecord<User> {

    private Integer id;
    private String name, email, login, password;
    private boolean active = true, admin;

    public Integer getId() {
        return id;
    }

    public User setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public boolean isAdmin() {
        return admin;
    }

    public User setAdmin(boolean admin) {
        this.admin = admin;
        return this;
    }

    public String getLogin() {
        return login;
    }

    public User setLogin(String login) {
        this.login = login;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getGravatar() {
        return GravatarUtil.gravatarURL(email.toLowerCase());
    }

    public boolean isActive() {
        return active;
    }

    public User setActive(boolean active) {
        this.active = active;
        return this;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", email=" + email + ", admin=" + admin
                + ", login=" + login + ", password=" + password + "]";
    }

    @Override
    public Object save() throws PersistenceException {
        return Factory.persistence.getUserDAO().save(this);
    }

    @Override
    public Integer update() throws PersistenceException {
        return Factory.persistence.getUserDAO().update(this);
    }

    @Override
    public Integer delete() throws PersistenceException {
        return Factory.persistence.getUserDAO().delete(this);
    }

    @Override
    public User one() {
        return Factory.persistence.getUserDAO().one(this);
    }

    @Override
    public List<? extends User> all() {
        return Factory.persistence.getUserDAO().all(this);
    }

    public User oneByLogin() {
        return Factory.persistence.getUserDAO().oneByLogin(this);
    }

    public List<Thread> assignedThread() {
        return Factory.persistence.getThreadDAO().allAssigned(this);
    }

    public List<? extends Subject> assignedSubjects() {
        return Factory.persistence.getSubjectDAO().allAssigned(this);
    }

    public List<? extends Subject> unassignedSubjects() {
        return Factory.persistence.getSubjectDAO().allUnassigned(this);
    }

}
