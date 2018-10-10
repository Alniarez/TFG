package persistence;

import model.Message;

import java.util.List;

public interface MessageDAO extends DAO<Message> {

    List<? extends Message> allByThread(Message message);

}
