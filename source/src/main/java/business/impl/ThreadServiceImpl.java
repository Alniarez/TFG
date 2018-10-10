package business.impl;

import business.ThreadService;
import business.exception.BusinessException;
import business.mail.Mailer;
import model.Message;
import model.Subject;
import model.Thread;
import model.User;
import persistence.exception.PersistenceException;

import java.util.Date;
import java.util.List;

public class ThreadServiceImpl implements ThreadService {

    @Override
    public List<? extends Thread> assignedThreads(User authenticatedUser) {
        return (authenticatedUser.isAdmin()) ? new Thread().all()
                : authenticatedUser.assignedThread();
    }

    @Override
    public void close(Integer id, User user) throws BusinessException {
        assertThreadAssigned(id, user);
        try {
            new Thread().setId(id).one().setOpen(false).update();
        } catch (PersistenceException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public void create(String name, String email, String subject, String topic, String text)
            throws BusinessException {
        Thread thread = new Thread().setName(name).setEmail(email)
                .setSubjectId(Integer.parseInt(subject)).setTopic(topic).setText(text)
                .setDate(new Date());
        try {
            thread.save();
            Mailer.sendNewThreadMail(thread);
        } catch (PersistenceException e) {
            throw new BusinessException(e.getMessage());
        }

    }

    @Override
    public void reply(Integer threadID, String text, User user) throws BusinessException {
        assertThreadAssigned(threadID, user);
        try {
            Message m = new Message().setDate(new Date()).setText(text).setUserId(user.getId())
                    .setThreadId(threadID);
            m.save();
            Mailer.sendNewReplyMail(m);
        } catch (PersistenceException e) {
            throw new BusinessException(e.getMessage());
        }

    }

    @Override
    public void changeSubject(Integer idThread, Integer idSubject, User user)
            throws BusinessException {
        assertThreadAssigned(idThread, user);
        Thread thread = new Thread().setId(idThread).one().setSubjectId(idSubject);
        try {
            thread.update();
            Mailer.sendNewThreadMail(thread);
        } catch (PersistenceException e) {
            throw new BusinessException(e.getMessage());
        }

    }

    @Override
    public void addSubject(String code) throws BusinessException {
        try {
            new Subject().setCode(code).save();
        } catch (PersistenceException e) {
            throw new BusinessException(e.getMessage());
        }

    }

    @Override
    public void deleteSubject(Integer id) throws BusinessException {
        try {
            new Subject().setId(id).delete();
        } catch (PersistenceException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    private void assertThreadAssigned(Integer threadID, User user) throws BusinessException {
        if (user.isAdmin())
            return;
        for (Thread t : assignedThreads(user))
            if (t.getId().equals(threadID))
                return;

        throw new BusinessException("error.009");
    }
}
