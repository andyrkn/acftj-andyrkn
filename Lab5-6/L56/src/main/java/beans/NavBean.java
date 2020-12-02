package beans;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.IOException;

@Named("NavBean")
@ApplicationScoped
public class NavBean {

    public NavBean() {}

    public void toMettings() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect("/meetings.xhtml");
    }

    public void toPersons() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect("/persons.xhtml");
    }

    public void toSolution() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect("/solution.xhtml");
    }

    public void toLocations() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect("/locations.xhtml");
    }

    public void toSearch() throws IOException{
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect("/meetings-search.xhtml");
    }
}
