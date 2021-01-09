package business;

import business.models.HistoryModel;
import business.models.MonitorableModel;
import domain.ItemState;
import domain.MonitorableItem;
import persistence.ItemStateRepo;
import persistence.MonitorableItemsRepo;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Named
public class ItemsService {

    @Inject
    private ItemStateRepo itemsRepo;

    @Inject
    private MonitorableItemsRepo monitorRepo;

    public List<MonitorableModel> getSpecific(String name) {
        MonitorableItem monitorable = monitorRepo.getItem(name);

        return monitorable.urls.stream().map(url -> {
            List<ItemState> states = itemsRepo.getAllStatesForItem(monitorable.name, url);
            if(states != null) {
                return new MonitorableModel(url, states);
            }
            return null;
        })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public List<HistoryModel> getAll(){
        List<MonitorableItem> items = monitorRepo.getAllDocuments();

        return items.stream().map(item -> {
            ItemState lastState = itemsRepo.getLast(item.name, item.urls);
            ItemState bestState = itemsRepo.getBest(item.name);

            if(lastState != null) {
                return new HistoryModel(item.id, item.name, lastState.price, bestState.price, bestState.date);
            }
            return null;
        })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
