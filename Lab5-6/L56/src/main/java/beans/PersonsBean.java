package beans;

import domain.Person;
import persistence.PersonsRepo;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class PersonsBean implements Serializable {

    private String name;

    @Inject
    private PersonsRepo repo;

    public PersonsBean() { }

    public List<Person> view() {
        List<Person> results =  repo.GetAll();
        return results;
    }

    public void savePerson() throws IOException {
        repo.InsertOne(new Person(name));
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(ec.getRequestContextPath());
    }

    public void setRepo(PersonsRepo repo) {
        this.repo = repo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
