package beans;

import domain.Location;
import domain.Person;
import persistence.LocationsRepo;

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
public class LocationBean implements Serializable {

    private String name;

    @Inject
    private LocationsRepo repo;

    public void saveLocation() throws IOException {
        repo.InsertOne(new Location(name));
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(ec.getRequestContextPath());
    }

    public List<Location> view() {
        List<Location> results =  repo.GetAll();
        return results;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
