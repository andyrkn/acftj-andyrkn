package persistence;

import ccc.MySqlEm;
import domain.Person;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;

@Named
@RequestScoped
public class PersonsRepo implements Serializable {

    @Inject
    @MySqlEm
    private EntityManager em;

    public PersonsRepo() { }

    public boolean InsertOne(Person entity) {
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

    public List<Person> GetAll() { return (List<Person>) em.createQuery("Select p from Person p").getResultList();
    }
}
