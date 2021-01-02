package domain;

import java.time.LocalDateTime;

public class ItemState extends IDocument {
    public String Url;
    public String Price;
    public LocalDateTime Date;

    public ItemState() { }

    public ItemState(String url, String price, LocalDateTime date) {
        Url = url;
        Price = price;
        Date = date;
    }

}