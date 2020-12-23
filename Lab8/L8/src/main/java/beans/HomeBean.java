package beans;

import domain.UserFile;
import org.primefaces.model.UploadedFile;
import persistence.FileRepo;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class HomeBean implements Serializable {

    @Inject transient private FileRepo repo;

    private UploadedFile file;

    public void save(){
        UserFile userFile = new UserFile(file.getFileName(), file.getContents());
        repo.InsertOne(userFile);
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }
}
