package domain;

import javax.persistence.*;
import java.io.Serializable;
import javax.persistence.Id;

@Entity
@Table
public class Person implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public int Id;

    @Column
    public String Name;

    public Person() {}

    public Person(String name) {
        Name = name;
    }

    public String getName() {
        return Name;
    }

    public Integer getId(){
        return Id;
    }
}
