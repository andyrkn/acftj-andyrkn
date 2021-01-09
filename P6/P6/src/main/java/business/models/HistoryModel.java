package business.models;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HistoryModel {

    public ObjectId id;
    public String name;
    public LocalDateTime date;
    public String price;
    public String bestPrice;

    public HistoryModel() { }

    public HistoryModel(ObjectId id, String name, String price, String bestPrice, LocalDateTime date) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.bestPrice = bestPrice;
        this.date = date;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getDate() {
        return date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBestPrice() {
        return bestPrice;
    }

    public void setBestPrice(String bestPrice) {
        this.bestPrice = bestPrice;
    }
}
