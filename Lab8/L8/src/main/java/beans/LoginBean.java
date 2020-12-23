package beans;


import business.PasswordHasher;
import decorators.IRegisterBean;
import domain.User;
import persistence.UserRepo;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ApplicationScoped
public class LoginBean implements Serializable {

    @Inject transient private PasswordHasher hasher;
    @Inject transient private UserRepo repo;

    private String name;
    private String password;
    private String role;
    private boolean loggedIn = false;

    public String getName() {
        return name;
    }

    public void login(){
        String hash = hasher.hash(password);
        User user = repo.findOne(name);

        if(user == null){
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(Boolean.toString(false)));
            return;
        }

        boolean success = hash.equals(user.passwordHash);

        if(success) {
            role = user.role;
            loggedIn = true;
            password = "";
        }
    }

    public void logout(){
        name = "";
        role = "";
        loggedIn = false;
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

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
}
