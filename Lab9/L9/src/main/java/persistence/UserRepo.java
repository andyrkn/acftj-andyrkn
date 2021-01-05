package persistence;

import domain.User;

import javax.inject.Singleton;
import java.io.Serializable;

import static com.mongodb.client.model.Filters.eq;

@Singleton
public class UserRepo extends MongoRepo<User> implements Serializable {


    public User findOne(String name){
        return GetCollection().find(eq("name", name)).first();
    }

    @Override
    protected Class<User> getDerivedClass() {
        return User.class;
    }
}
