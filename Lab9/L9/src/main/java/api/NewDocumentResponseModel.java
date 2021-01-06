package api;

public class NewDocumentResponseModel {
    public String id;

    public NewDocumentResponseModel() { }

    public NewDocumentResponseModel(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
