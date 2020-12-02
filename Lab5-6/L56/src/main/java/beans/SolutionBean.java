package beans;

import business.MeetingSolver;
import business.Solution;
import domain.Meeting;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleModel;
import persistence.MeetingsRepo;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Named("SolutionBean")
@RequestScoped
public class SolutionBean {

    private List<Meeting> Meetings;
    private ScheduleModel eventModel;
    private Solution Solution;

    @Inject
    private MeetingsRepo mRepo;

    public SolutionBean() { }

    @PostConstruct
    public void postConstructor()
    {
        Meetings = mRepo.GetAll();
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
