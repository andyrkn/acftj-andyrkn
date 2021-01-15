package beans;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.IOException;

@Named("NavBean")
@ApplicationScoped
public class NavBean {

    public void toHome() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect("/home.xhtml");
    }
    public void toAdd() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect("/item-add.xhtml");
    }

    public void toSearch() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect("/item-search.xhtml");
    }

    public void toItemChart() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect("/item-chart.xhtml");
    }
}
