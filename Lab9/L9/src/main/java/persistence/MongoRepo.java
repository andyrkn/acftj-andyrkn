package persistence;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import domain.IDocument;
import interceptors.Log;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import static com.mongodb.client.model.Filters.eq;

abstract class MongoRepo<T extends IDocument> {

    private static MongoClient mongoClient = null;
    private static MongoDatabase database = null;

    @PostConstruct
    public void init() {
        if(mongoClient == null) {
            CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
            CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);

            mongoClient = MongoClients.create(MongoClientSettings.builder()
                    .applyConnectionString(new ConnectionString("mongodb://root:root@localhost:27017/admin"))
                    .codecRegistry(codecRegistry)
                    .build());

            database = mongoClient.getDatabase("JavaLabs");
        }
    }

    @Log
    public boolean InsertOne(T document){

        GetCollection().insertOne(document);
        return true;
    }

    public T GetById(ObjectId id) {
        return GetCollection().find(eq("_id", id)).first();
    }

    public T Delete(ObjectId id) {
        return GetCollection().findOneAndDelete(eq("_id", id));
    }

    public T Update(ObjectId id, T document) {
        return GetCollection().findOneAndReplace(eq("_id", id), document);
    }

    public ArrayList<T> GetAllDocuments() {

        ArrayList<T> documents = new ArrayList<>();

        MongoCursor<T> iterable = GetCollection().find().iterator();
        while(iterable.hasNext())
        {
            documents.add(iterable.next());
        }

        return documents;
    }

    protected MongoCollection<T> GetCollection() { return database.getCollection(getDerivedClass().getName(), getDerivedClass());
    }

    protected abstract Class<T> getDerivedClass();
}
