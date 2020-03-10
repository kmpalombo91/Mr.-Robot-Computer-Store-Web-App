package edu.slcc.asdv.beans;

import bl.singleton.dao.generic.WarehouseSingleton;
import java.sql.SQLException;
import javax.inject.Named;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Destroyed;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;

@Named(value = "initializationBean")
@ApplicationScoped
public class InitializationBean {
    private static WarehouseSingleton ws;
    
    public void init(@Observes @Initialized(ApplicationScoped.class) Object init) throws SQLException {
        System.out.println("initialize singleton");
        ws = WarehouseSingleton.instantiateWarehouse();
    }
    
    public static WarehouseSingleton getWarehouse() {
        return ws;
    }
    
    public void destroy(@Observes @Destroyed(ApplicationScoped.class) Object init) {
        // cleanup and save
    }
}
