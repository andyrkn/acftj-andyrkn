package business.models;

import domain.ItemState;
import java.util.List;

public class MonitorableModel {
    public String url;
    public List<ItemState> states;

    public MonitorableModel(String url, List<ItemState> states) {
        this.url = url;
        this.states = states;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<ItemState> getStates() {
        return states;
    }

    public void setStates(List<ItemState> states) {
        this.states = states;
    }
}
