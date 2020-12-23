package beans;

import business.PasswordHasher;
import decorators.IRegisterBean;
import domain.User;
import persistence.UserRepo;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDateTime;

@Named
@SessionScoped
public class RegisterBean implements Serializable, IRegisterBean {

    @Inject private AdminBean adminBean;
    @Inject transient private PasswordHasher hasher;
    @Inject transient private UserRepo repo;

    private String name;
    private String password;
    private String role;

    public String getName() {
        return name;
    }

    public void save(){
        String hash = hasher.hash(password);
        User user = new User(name, hash, role);
        boolean success = repo.InsertOne(user);

        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(Boolean.toString(success)));
    }

    public boolean getCanRegister(){
        if(adminBean.isEnabled()){
            if(LocalDateTime.now().getHour() < adminBean.getStartHour()){
                return false;
            }
            if(LocalDateTime.now().getHour() > adminBean.getEndHour()){
                return false;
            }
            return true;
        }
        return true;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
