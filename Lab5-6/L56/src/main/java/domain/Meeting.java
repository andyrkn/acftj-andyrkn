package domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table
public class Meeting implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Integer Id;

    @Column
    public String Name;

    @ManyToMany
    public List<Person> Attendees = new ArrayList<>();

    @Column
    public int Duration;

    @Column
    public int StartingTime;

    @OneToOne
    @JoinColumn(name = "location_id")
    public Location Location;

    public Meeting() {
    }

    public Meeting(String name, int duration, Location location) {
        Name = name;
        Duration = duration;
        Location = location;
    }

    public Meeting(String name) {
        Name = name;
    }

    public String getName() {
        return Name;
    }

    public List<String> getAttendees() {
        return Attendees.stream().map(a -> a.Name).collect(Collectors.toList());
    }

    public List<Person> getPersons() {
        return Attendees;
    }

    public int getDuration() {
        return Duration;
    }

    public int getStartingTime() {
        return StartingTime;
    }

    public Integer getId(){
        return Id;
    }

    public String getLocation() {
        if(Location == null){
            return "";
        }
        return Location.getName();
    }
}