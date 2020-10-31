package beans;

import domain.Person;
import persistence.PersonsRepo;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.ArrayList;

@ManagedBean(name = "PersonsBean", eager = true)
@RequestScoped
public class PersonsBean {

    private String name;

    private PersonsRepo repo;

    public PersonsBean() {
        repo = new PersonsRepo();
    }

    public ArrayList<Person> persons() {
        ArrayList<Person> results =  repo.GetAllDocuments(Person.class);
        return results;
    }

    public void savePerson() throws IOException {
        repo.InsertOne(new Person(name), Person.class);
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(ec.getRequestContextPath());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
