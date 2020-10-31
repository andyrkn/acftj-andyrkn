package beans;

import business.MeetingSolver;
import business.Solution;
import domain.Meeting;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleModel;
import persistence.MeetingsRepo;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

@ManagedBean(name = "SolutionBean", eager = true)
@RequestScoped
public class SolutionBean {

    private ArrayList<Meeting> Meetings;
    private ScheduleModel eventModel;
    private Solution Solution;
    private MeetingsRepo mRepo;

    public SolutionBean() {
        mRepo = new MeetingsRepo();
        Meetings = mRepo.GetAllDocuments(Meeting.class);
        MeetingSolver solver = new MeetingSolver(Meetings);
        Solution = solver.solve();
        init();
    }

    public void init(){
        if(!Solution.Success){
            eventModel = new DefaultScheduleModel();
            return;
        }
        int[] t = Solution.Timetable;
        eventModel = new DefaultScheduleModel();

        for(int i = 0; i< t.length; i++){
            DefaultScheduleEvent event = new DefaultScheduleEvent(
                    Meetings.get(i).Name,
                    today(t[i]),
                    today(t[i] + Meetings.get(i).Duration));
            eventModel.addEvent(event);
        }
    }

    private Date today(int hour){
        return Date.from(LocalDateTime.now().withHour(hour + 9).withMinute(0).withSecond(0).withNano(0).toInstant(ZoneOffset.ofHours(0)));
    }

    public String getResults() {
        String result = Arrays.stream(Solution.Timetable)
                .mapToObj(t -> {return String.valueOf(t);})
                .collect(Collectors.joining(","));

        return result;
    }

    public void setEventModel(ScheduleModel eventModel) {
        this.eventModel = eventModel;
    }

    public void setSolution(business.Solution solution) {
        Solution = solution;
    }

    public ScheduleModel getEventModel() {
        return eventModel;
    }

    public business.Solution getSolution() {
        return Solution;
    }
}
