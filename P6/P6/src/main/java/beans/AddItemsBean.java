package beans;

import domain.MonitorableItem;
import persistence.MonitorableItemsRepo;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;

@Named
@ApplicationScoped
public class AddItemsBean {

    private String itemName;

    @Inject
    private MonitorableItemsRepo repo;

    public void save() {
        // mock item
        ArrayList<String> list = new ArrayList<>();
        list.add("https://www.emag.ro/placa-video-evga-geforce-rtx-2060-super-sc-ultra-gaming-8gb-gddr6-256-bit-08g-p4-3067-kr/pd/DLLF93MBM/?X-Search-Id=d3e68812c683daf89c01&X-Product-Id=6882128&X-Search-Page=1&X-Search-Position=1&X-Section=search&X-MB=0&X-Search-Action=view");
        list.add("https://www.emag.ro/placa-video-asus-dual-geforce-rtxtm-2060-mini-oc-6gb-gddr6-192-bit-dual-rtx2060-o6g-mini/pd/DF7Q1YMBM/?X-Search-Id=d3e68812c683daf89c01&X-Product-Id=6768158&X-Search-Page=1&X-Search-Position=2&X-Section=search&X-MB=0&X-Search-Action=view");
        repo.InsertOne(new MonitorableItem("RTX 2060", list));
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
