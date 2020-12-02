package beans;

import domain.Location;
import domain.Meeting;
import persistence.LocationsRepo;
import persistence.MeetingsRepo;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@Named("MeetingBean")
@SessionScoped
public class MeetingBean implements Serializable {

    private String name;
    private int duration;
    private int personId;
    private int meetingId;

    private int locationId;
    private int[] Results;

    @Inject
    private MeetingsRepo mRepo;

    @Inject
    private LocationsRepo lRepo;

    public MeetingBean() {
    }

    public List<Meeting> view(){
        return mRepo.GetAll();
    }

    public void saveMeeting() throws IOException {
        Location l = lRepo.Get(locationId);
        if(l != null){
            mRepo.InsertOne(new Meeting(name, duration, l));
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(ec.getRequestContextPath());
        }
    }

    public void addAttendee() throws IOException {
        mRepo.addAttendee(meetingId, personId);
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(ec.getRequestContextPath());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setPersonId(int personId) { this.personId = personId; }

    public void setMeetingId(int meetingId) {
        this.meetingId = meetingId;
    }

    public int getPersonId() {
        return personId;
    }

    public int getMeetingId() {
        return meetingId;
    }

    public int[] getResults() {
        return Results;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }
}
