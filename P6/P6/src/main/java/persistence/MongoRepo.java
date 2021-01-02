package persistence;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import domain.IDocument;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

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

    public boolean InsertOne(T document){

        GetCollection().insertOne(document);
        return true;
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

    protected MongoCollection<T> GetCollection() { return database.getCollection(getDerivedClass().getName(), getDerivedClass()); }

    protected MongoCollection<T> GetCollection(String collection) { return database.getCollection(collection, getDerivedClass()); }

    protected abstract Class<T> getDerivedClass();
}
