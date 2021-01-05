package business;

import com.google.common.hash.Hashing;

import javax.inject.Singleton;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;

@Singleton
public class PasswordHasher implements Serializable {

    public String hash(String input){
        return Hashing.sha256()
                .hashString(input, StandardCharsets.UTF_8)
                .toString();
    }
}
