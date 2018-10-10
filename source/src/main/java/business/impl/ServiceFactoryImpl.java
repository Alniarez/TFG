package business.impl;

import business.ServiceFactory;
import business.ThreadService;
import business.UserService;

public class ServiceFactoryImpl implements ServiceFactory {

    @Override
    public ThreadService getThreadService() {
        return new ThreadServiceImpl();
    }

    @Override
    public UserService getUserService() {
        return new UserServiceImpl();
    }

}
