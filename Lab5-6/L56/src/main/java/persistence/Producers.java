package persistence;

import ccc.MySqlEm;

import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class Producers {

    @Produces
    @MySqlEm
    public EntityManager getEntityManager() {
        return Persistence
                .createEntityManagerFactory("persUnit")
                .createEntityManager();
    }

    public void close(@Disposes @MySqlEm EntityManager em) {
        em.close();
    }
}

