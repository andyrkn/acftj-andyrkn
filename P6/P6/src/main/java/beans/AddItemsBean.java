package beans;

import business.ScrapItemsService;
import domain.MonitorableItem;
import persistence.MonitorableItemsRepo;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@ApplicationScoped
public class AddItemsBean {

    private String itemName;

    @Inject
    private ScrapItemsService scrapper;

    @Inject
    private MonitorableItemsRepo repo;

    public void save() {
        try {
            List<String> urls = scrapper.scrapEmag(itemName);
            repo.InsertOne(new MonitorableItem(itemName, urls));
        }
        catch (Exception ignored){ }
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
