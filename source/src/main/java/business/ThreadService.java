package business;

import business.exception.BusinessException;
import model.Thread;
import model.User;

import java.util.List;

public interface ThreadService {

    List<? extends Thread> assignedThreads(User authenticatedUser);

    void reply(Integer threadID, String text, User user) throws BusinessException;

    void close(Integer id, User user) throws BusinessException;

    void create(String name, String email, String subject, String topic, String text)
            throws BusinessException;

    void changeSubject(Integer idThread, Integer idSubject, User user) throws BusinessException;

    void addSubject(String code) throws BusinessException;

    void deleteSubject(Integer id) throws BusinessException;
}
