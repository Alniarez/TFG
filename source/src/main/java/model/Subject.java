package model;

import common.Factory;
import persistence.exception.PersistenceException;

import java.util.List;

public class Subject implements ActiveRecord<Subject> {

    private Integer id;
    private String code;

    public Subject() {
    }

    public Subject(Integer id, String code) {
        super();
        this.id = id;
        this.code = code;
    }

    public Integer getId() {
        return id;
    }

    public Subject setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getCode() {
        return code;
    }

    public Subject setCode(String code) {
        this.code = code;
        return this;
    }

    @Override
    public Object save() throws PersistenceException {
        return Factory.persistence.getSubjectDAO().save(this);
    }

    @Override
    public Integer update() throws PersistenceException {
        return Factory.persistence.getSubjectDAO().update(this);
    }

    @Override
    public Integer delete() throws PersistenceException {
        return Factory.persistence.getSubjectDAO().delete(this);
    }

    @Override
    public Subject one() {
        return Factory.persistence.getSubjectDAO().one(this);
    }

    @Override
    public List<? extends Subject> all() {
        return Factory.persistence.getSubjectDAO().all(this);
    }

}
