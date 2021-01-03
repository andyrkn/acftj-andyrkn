package domain;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

public abstract class IDocument {

    @BsonProperty("_id")
    @BsonId
    public ObjectId id;
}
