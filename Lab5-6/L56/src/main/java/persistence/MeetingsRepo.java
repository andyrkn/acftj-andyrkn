package persistence;

import ccc.MySqlEm;
import domain.Location;
import domain.Meeting;
import domain.Person;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class MeetingsRepo implements Serializable {

    @Inject
    @MySqlEm
    private EntityManager em;

    public boolean addAttendee(Integer meetingId, Integer personId) {
        try{
            em.getTransaction().begin();
            Meeting m = em.getReference(Meeting.class, meetingId);

            Person p = em.getReference(Person.class, personId); if(p == null){
                em.getTransaction().rollback();
                return false;
            }

            m.Attendees.add(p);
            em.getTransaction().commit();
        }
        catch (Exception e){
            throw e;
        }
        return true;
    }

    public boolean InsertOne(Meeting entity) {
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

    public List<Meeting> GetAll() {
        return em.createQuery("Select m from Meeting m").getResultList();
    }

    public List<Meeting> Filter(int duration, String name,  String location) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Meeting> query = builder.createQuery(Meeting.class);
        Root<Meeting> root = query.from(Meeting.class);
        root.fetch("Location");
        List<Predicate> predicates = new ArrayList<>();

        if(duration != -1){
            predicates.add(builder.equal(root.get("Duration"), duration));
        }

        if(name != null){
            predicates.add(builder.like(root.get("Name"),"%" + name +"%"));
        }

        if(location != null) {
            predicates.add(builder.like(root.get("Location").get("Name"), "%" + location +"%"));
        }

        return em.createQuery(query.where(predicates.toArray(new Predicate[]{}))).getResultList();
    }
}
