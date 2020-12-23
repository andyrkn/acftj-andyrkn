package beans;

import domain.UserFile;
import events.TimeframeEvent;
import persistence.FileRepo;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.List;

@Named
@ApplicationScoped
public class AdminBean implements Serializable {

    @Min(value = 0, message = "Can't be less than 0")
    @Max(value = 23, message = "Can't be bigger than 23")
    private int startHour;

    @Min(value = 1, message = "Can't be less than 1")
    @Max(value = 24, message = "Can't be bigger than 23")
    private int endHour;

    private boolean enabled = false;

    @Inject private LoginBean loginBean;
    @Inject transient private FileRepo repo;

    @Inject @TimeframeEvent
    private Event<Boolean> statusEvents;

    public void toggle(){
        statusEvents.fire(enabled);
    }

    public boolean isValidRole(){
        if (loginBean.isLoggedIn()){
            return loginBean.getRole().equals("admin");
        }
        return false;
    }

    public List<UserFile> view(){
        return repo.GetAllDocuments();
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
