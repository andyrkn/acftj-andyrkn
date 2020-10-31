package domain;

import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.types.ObjectId;

public class Person extends IDocument {
    public String Name;

    public Person() {}

    public Person(String name) {
        Name = name;
    }

    @BsonIgnore
    public String getName() {
        return Name;
    }

    @BsonIgnore
    public ObjectId getId(){
        return Id;
    }
}
