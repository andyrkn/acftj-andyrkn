package persistence;

import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Sorts;
import domain.ItemState;

import javax.inject.Singleton;
import java.util.*;

import static com.mongodb.client.model.Filters.*;

@Singleton
public class ItemStateRepo extends MongoRepo<ItemState> {

    public boolean insertSpecificItem(ItemState document, String item) {

        GetCollection("items_"+item).insertOne(document);
        return true;
    }

    public ItemState getLast(String item, List<String> urls){
        Optional<ItemState> state = urls.stream().map(url -> {
            return GetCollection("items_"+item)
                    .find(eq("url", url))
                    .sort(Sorts.descending("date"))
                    .limit(1)
                    .first();
        })
                .filter(Objects::nonNull)
                .min(Comparator.comparing(ItemState::getPrice));

        return state.orElse(null);
    }

    public ItemState getBest(String item) {
        return GetCollection("items_"+item)
                .find()
                .sort(Sorts.ascending("price"))
                .limit(1)
                .first();
    }

    public ArrayList<ItemState> getAllStatesForItem(String item, String url) {
        ArrayList<ItemState> documents = new ArrayList<>();

        MongoCursor<ItemState> iterable = GetCollection("items_"+item)
                .find((eq("url",url)))
                .sort(Sorts.ascending("date"))
                .iterator();
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
