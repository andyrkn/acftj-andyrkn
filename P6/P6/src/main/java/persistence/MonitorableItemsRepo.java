package persistence;

import domain.MonitorableItem;
import javax.inject.Singleton;

import static com.mongodb.client.model.Filters.eq;

@Singleton
public class MonitorableItemsRepo extends MongoRepo<MonitorableItem> {

    public MonitorableItem getItem(String name) {
        return GetCollection()
                .find(eq("name",name))
                .first();
    }

    @Override
    protected Class<MonitorableItem> getDerivedClass() {
        return MonitorableItem.class;
    }
}
