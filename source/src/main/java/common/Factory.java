package common;

import business.ServiceFactory;
import business.impl.ServiceFactoryImpl;
import persistence.PersistenceFactory;
import persistence.impl.PersistenceFactoryImpl;

public class Factory {

    public final static PersistenceFactory persistence = new PersistenceFactoryImpl();
    public final static ServiceFactory sercive = new ServiceFactoryImpl();

}
