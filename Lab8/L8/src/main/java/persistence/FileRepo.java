package persistence;

import domain.UserFile;

import javax.inject.Singleton;
import java.io.Serializable;

@Singleton
public class FileRepo extends MongoRepo<UserFile> implements Serializable {



    @Override
    protected Class<UserFile> getDerivedClass() {
        return UserFile.class;
    }
}
