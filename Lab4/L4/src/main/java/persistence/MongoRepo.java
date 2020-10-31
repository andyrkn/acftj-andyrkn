package persistence;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import com.sun.xml.internal.bind.v2.model.core.ID;
import domain.IDocument;
import domain.Meeting;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.ArrayList;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

abstract class MongoRepo<T extends IDocument> {

    private static MongoClient mongoClient = null;
    private static MongoDatabase database = null;

    public MongoRepo() {
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

    public boolean InsertOne(T document, Class<T> type){

        GetCollection(type).insertOne(document);
        return true;
    }

    public ArrayList<T> GetAllDocuments(Class<T> type) {

        ArrayList<T> documents = new ArrayList<>();

        MongoCursor<T> iterable = GetCollection(type).find().iterator();
        while(iterable.hasNext())
        {
            documents.add(iterable.next());
        }

        return documents;
    }

    protected MongoCollection<T> GetCollection(Class<T> type){ return database.getCollection(type.getName(), type);
    }
}
