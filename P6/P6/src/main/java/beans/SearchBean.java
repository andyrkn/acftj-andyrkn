package beans;

import business.ItemsService;
import business.models.MonitorableModel;
import domain.ItemState;
import domain.MonitorableItem;
import persistence.ItemStateRepo;
import persistence.MonitorableItemsRepo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class SearchBean {

    @Inject
    ItemsService service;

    @Inject
    ItemStateRepo stateRepo;

    public List<ItemState> view(){
        return stateRepo.getAllStatesForItem(itemName, url);
    }

    private String itemName;
    private String url;

    public List<ItemState> getView() { return stateRepo.getAllStatesForItem(itemName, url); }

    public List<MonitorableModel> getItem() {
        return service.getSpecific(itemName);
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
