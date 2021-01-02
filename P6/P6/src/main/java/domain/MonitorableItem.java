package domain;

import java.util.List;

public class MonitorableItem extends IDocument {

    public String Name;
    public List<String> Urls;

    public MonitorableItem() {}

    public MonitorableItem(String name, List<String> urls) {
        Name = name;
        Urls = urls;
    }
}
