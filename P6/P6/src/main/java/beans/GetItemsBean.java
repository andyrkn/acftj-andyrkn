package beans;

import domain.ItemState;
import domain.MonitorableItem;
import persistence.ItemStateRepo;
import persistence.MonitorableItemsRepo;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class GetItemsBean {

    private String itemName;
    private String url;

    @Inject
    MonitorableItemsRepo itemsRepo;

    @Inject
    ItemStateRepo stateRepo;

    public List<ItemState> view(){
        return stateRepo.GetAllStatesForItem(itemName, url);
    }

    public List<MonitorableItem> getMonitorableItems() {
        return itemsRepo.GetAllDocuments();
    }

    public List<ItemState> getView() {
        return stateRepo.GetAllStatesForItem(itemName, url);
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
