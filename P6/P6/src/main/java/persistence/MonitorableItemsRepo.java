package persistence;

import domain.MonitorableItem;

import javax.inject.Singleton;

@Singleton
public class MonitorableItemsRepo extends MongoRepo<MonitorableItem> {


    @Override
    protected Class<MonitorableItem> getDerivedClass() {
        return MonitorableItem.class;
    }
}
