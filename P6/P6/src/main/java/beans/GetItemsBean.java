package beans;

import business.ItemsService;
import business.models.HistoryModel;
import persistence.ItemStateRepo;
import persistence.MonitorableItemsRepo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class GetItemsBean {

    @Inject
    ItemsService service;

    @Inject
    MonitorableItemsRepo itemsRepo;

    @Inject
    ItemStateRepo stateRepo;

    public List<HistoryModel> getView() {
        return service.getAll();
    }

}

