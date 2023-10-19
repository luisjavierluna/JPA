package servicios;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class LibreriaServicio {
    
    public EntityManager crearEntityManager() {
        EntityManager em = Persistence
                    .createEntityManagerFactory("JPAPU")
                    .createEntityManager();
        
        return em;
    }
    
}
