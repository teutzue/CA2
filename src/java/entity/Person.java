package entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

/**
 *
 * @author CosticaTeodor
 */
@Entity
@DiscriminatorValue(value = "P")
public class Person extends InfoEntity {

    private String firstName;
    private String lastName;
    @ManyToMany(mappedBy = "persons", cascade = CascadeType.PERSIST)
    private List<Hobby> hobbys = new ArrayList<>();

    public Person() {}
    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Hobby> getHobbys() {
        return hobbys;
    }

    public void setHobbys(List<Hobby> hobbys) {
        this.hobbys = hobbys;
    }

    public void addHobby(Hobby h) {
        if (!getHobbys().contains(h)) {
            hobbys.add(h);
        }
        if (!h.getPersons().contains(this)) {
            h.getPersons().add(this);
        }
    }

}
