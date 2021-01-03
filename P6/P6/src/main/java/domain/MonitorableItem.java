package domain;

import java.util.List;

public class MonitorableItem extends IDocument {

    public String name;
    public List<String> urls;

    public MonitorableItem() {}

    public MonitorableItem(String name, List<String> urls) {
        this.name = name;
        this.urls = urls;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }
}
