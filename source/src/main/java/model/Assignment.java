package model;

import common.Factory;
import persistence.exception.PersistenceException;

import java.util.List;

public class Assignment implements ActiveRecord<Assignment> {

    private Integer userId, subjectId;

    public Integer getUserId() {
        return userId;
    }

    public Assignment setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public Assignment setSubjectId(Integer subject_Id) {
        this.subjectId = subject_Id;
        return this;
    }

    @Override
    public Object save() throws PersistenceException {
        return Factory.persistence.getAssignmentDAO().save(this);
    }

    @Override
    public Integer update() throws PersistenceException {
        return Factory.persistence.getAssignmentDAO().update(this);
    }

    @Override
    public Integer delete() throws PersistenceException {
        return Factory.persistence.getAssignmentDAO().delete(this);
    }

    @Override
    public Assignment one() {
        return Factory.persistence.getAssignmentDAO().one(this);
    }

    @Override
    public List<? extends Assignment> all() {
        return Factory.persistence.getAssignmentDAO().all(this);
    }

    public List<? extends Assignment> allByUserId() {
        return Factory.persistence.getAssignmentDAO().allByUserId(this);
    }

}
