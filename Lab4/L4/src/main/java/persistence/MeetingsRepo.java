package persistence;

import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import domain.Meeting;
import org.bson.Document;
import org.bson.types.ObjectId;
import static com.mongodb.client.model.Filters.eq;

public class MeetingsRepo extends MongoRepo<Meeting> {

    public boolean addAttendee(ObjectId meetingId, ObjectId id){

        Meeting meeting = GetCollection(Meeting.class)
                .find(eq("_id", meetingId))
                .first();

        if(meeting == null){
            return false;
        }

        if(meeting.Attendees.contains(id)){
            return true;
        }

        GetCollection(Meeting.class).updateOne(eq("_id", meetingId), Updates.addToSet("Attendees",id));
        return true;
    }
}
