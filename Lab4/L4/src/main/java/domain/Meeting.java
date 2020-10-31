package domain;

import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.types.ObjectId;

import java.util.ArrayList;

public class Meeting extends IDocument {
    public String Name;
    public ArrayList<ObjectId> Attendees = new ArrayList<>();
    public int Duration;
    public int StartingTime;

    public Meeting() {
    }

    public Meeting(String name, int duration) {
        Name = name;
        Duration = duration;
    }

    public Meeting(String name) {
        Name = name;
    }

    @BsonIgnore
    public String getName() {
        return Name;
    }

    @BsonIgnore
    public ArrayList<ObjectId> getAttendees() {
        return Attendees;
    }

    @BsonIgnore
    public int getDuration() {
        return Duration;
    }

    @BsonIgnore
    public int getStartingTime() {
        return StartingTime;
    }

    @BsonIgnore
    public ObjectId getId(){
        return Id;
    }
}
