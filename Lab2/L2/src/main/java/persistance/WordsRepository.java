package persistance;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import domain.Word;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.ArrayList;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public final class WordsRepository implements IWordsRepository{

    private MongoClient mongoClient;
    private MongoDatabase database;

    public WordsRepository() {

        CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);

        mongoClient = MongoClients.create(MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString("mongodb://root:root@localhost:27017/admin"))
                .codecRegistry(codecRegistry)
                .build());

        database = mongoClient.getDatabase("JavaLabs");
    }

    public boolean AddWord(Word word){
        Word result = GetCollection().find(and(eq("Word",word.Word),eq("Language",word.Language))).first();
        if(result == null) {
            GetCollection().insertOne(word);
            return true;
        }
        return false;
    }

    public ArrayList<Word> GetAllWords() {

        ArrayList<Word> words = new ArrayList<>();

        MongoCursor<Word> iterable = GetCollection().find().iterator();
        while(iterable.hasNext())
        {
            words.add(iterable.next());
        }

        return words;
    }

    private MongoCollection<Word> GetCollection(){
        return database.getCollection("Words", Word.class);
    }
}
