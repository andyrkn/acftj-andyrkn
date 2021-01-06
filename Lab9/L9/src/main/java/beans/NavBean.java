package beans;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.IOException;

@Named("NavBean")
@ApplicationScoped
public class NavBean {

    public void toLogin() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect("/L9-1.0-SNAPSHOT/login.xhtml");
    }

    public void toRegister() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect("/L9-1.0-SNAPSHOT/register.xhtml");
    }

    public void toAdmin() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect("/L9-1.0-SNAPSHOT/admin.xhtml");
    }

    public void toHome() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect("/L9-1.0-SNAPSHOT/home.xhtml");
    }
}
