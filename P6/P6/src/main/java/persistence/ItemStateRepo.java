package persistence;

import com.mongodb.client.MongoCursor;
import domain.ItemState;

import javax.inject.Singleton;
import java.util.ArrayList;

@Singleton
public class ItemStateRepo extends MongoRepo<ItemState> {

    public boolean InsertSpecificItem(ItemState document, String item) {

        GetCollection("items_"+item).insertOne(document);
        return true;
    }

    public ArrayList<ItemState> GetAllStatesForItem(String item) {
        ArrayList<ItemState> documents = new ArrayList<>();

        MongoCursor<ItemState> iterable = GetCollection("items_"+item).find().iterator();
        while(iterable.hasNext())
        {
            documents.add(iterable.next());
        }

        return documents;
    }

    @Override
    protected Class<ItemState> getDerivedClass() {
        return ItemState.class;
    }
}
