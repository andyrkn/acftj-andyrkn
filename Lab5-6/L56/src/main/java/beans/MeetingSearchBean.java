package beans;

import domain.Meeting;
import persistence.MeetingsRepo;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class MeetingSearchBean implements Serializable {

    public MeetingSearchBean() {
    }

    @Inject
    private MeetingsRepo mRepo;

    private boolean isLocation;
    private boolean isName;
    private boolean isDuration;

    private int DurationInput;
    private String LocationInput = "";
    private String NameInput = "";

    public List<Meeting> view(){
        int dSearch = isDuration ? DurationInput : -1;
        String nSearch = isName  ? NameInput == "" ?  null : NameInput : null;
        String lSearch = isLocation  ? LocationInput == "" ? null : LocationInput : null;
        return mRepo.Filter(dSearch, nSearch, lSearch);
    }

    public void search() {}

    public boolean isLocation() {
        return isLocation;
    }

    public void setLocation(boolean location) {
        isLocation = location;
    }

    public boolean isName() {
        return isName;
    }

    public void setName(boolean name) {
        isName = name;
    }

    public boolean isDuration() {
        return isDuration;
    }

    public void setDuration(boolean duration) {
        isDuration = duration;
    }


    public String getLocationInput() {
        return LocationInput;
    }

    public void setLocationInput(String locationInput) {
        LocationInput = locationInput;
    }

    public int getDurationInput() {
        return DurationInput;
    }

    public void setDurationInput(int durationInput) {
        DurationInput = durationInput;
    }

    public String getNameInput() {
        return NameInput;
    }

    public void setNameInput(String nameInput) {
        NameInput = nameInput;
    }
}
