package beans;

import domain.Meeting;
import org.bson.types.ObjectId;
import persistence.MeetingsRepo;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.ArrayList;

@ManagedBean(name = "MeetingBean", eager = true)
@RequestScoped
public class MeetingBean {

    private String name;
    private int duration;
    private ObjectId personId = null;
    private ObjectId meetingId = null;
    private int[] Results;

    private MeetingsRepo mRepo;

    public MeetingBean() {
        mRepo = new MeetingsRepo();
    }

    public ArrayList<Meeting> meetings(){
        return mRepo.GetAllDocuments(Meeting.class);
    }

    public void saveMeeting() throws IOException {
        mRepo.InsertOne(new Meeting(name, duration), Meeting.class);
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(ec.getRequestContextPath());
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

    public void setPersonId(String personId) {
        this.personId = new ObjectId(personId);
    }

    public void setMeetingId(String meetingId) {
        this.meetingId = new ObjectId(meetingId);
    }

    public String getPersonId() {
        if(this.personId == null) return "";
        return personId.toString();
    }

    public String getMeetingId() {
        if(this.meetingId == null) return "";
        return meetingId.toString();
    }

    public int[] getResults() {
        return Results;
    }
}
