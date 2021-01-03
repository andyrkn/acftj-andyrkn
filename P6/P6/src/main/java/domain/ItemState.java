package domain;

import java.time.LocalDateTime;

public class ItemState extends IDocument {
    public String url;
    public String price;
    public LocalDateTime date;

    public ItemState() { }

    public ItemState(String url, String price, LocalDateTime date) {
        this.url = url;
        this.price = price;
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}