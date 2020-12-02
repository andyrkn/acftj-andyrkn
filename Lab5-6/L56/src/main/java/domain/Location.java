package domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
public class Location implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public int Id;

    @Column
    public String Name;

    public Location() {}
    public Location(String name){
        Name = name;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
