package business;

public interface ServiceFactory {

    ThreadService getThreadService();

    UserService getUserService();

}
