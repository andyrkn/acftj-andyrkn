package persistence;

import ccc.MySqlEm;
import domain.Location;
import domain.Meeting;
import domain.Person;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

@Named
@RequestScoped
public class LocationsRepo implements Serializable {

    @Inject
    @MySqlEm
    private EntityManager em;

    public boolean InsertOne(Location entity) {
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        }
        catch (Exception e){
            throw e;
        }
        return true;
    }

    public Location Get(int id){
        return em.find(Location.class, id);
    }

    public List<Location> GetAll() {
        return em.createQuery("Select l from Location l").getResultList();
    }
}
