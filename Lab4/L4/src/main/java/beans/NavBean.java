package beans;

import javax.faces.bean.ManagedBean;
import java.io.IOException;

@ManagedBean(name = "NavBean", eager = true)
public class NavBean {
    public String get(){
        return "Heeeeeeeeeey navigations";
    }

    public String toMettings() throws IOException {

        return "/meetings.xhtml";
    }

    public String toPersons() throws IOException {

        return  "/persons.xhtml";
    }
}
