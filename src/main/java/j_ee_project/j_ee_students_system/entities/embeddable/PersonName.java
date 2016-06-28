package j_ee_project.j_ee_students_system.entities.embeddable;

import j_ee_project.j_ee_students_system.security.validation.ValidPartOfName;
import j_ee_project.j_ee_students_system.security.validation.ValidUsername;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Lightning
 */
@Embeddable
@XmlRootElement
public class PersonName {

   // @ValidPartOfName
    @Column(name = "first_name", length = MAX_FIRST_NAME_SIZE)
    String firstName;

//    @ValidPartOfName(message = "{j_ee_project.j_ee_students_system.security.validation.ValidSurname}")
    @Column(name = "surname", length = MAX_SURNAME_SIZE)
    String surname;

 //   @ValidPartOfName(message = "{j_ee_project.j_ee_students_system.security.validation.ValidSurname}")
    @Column(name = "last_name", length = MAX_LAST_NAME_SIZE)
    String lastName;

    @Transient
    final private static int MAX_FIRST_NAME_SIZE = 255;

    @Transient
    final private static int MAX_SURNAME_SIZE = 255;

    @Transient
    final private static int MAX_LAST_NAME_SIZE = 255;

    public PersonName() {
    }

    public PersonName(String firstName, String surname, String lastName) {
        this.firstName = firstName;
        this.surname = surname;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (this.firstName != null ? this.firstName.hashCode() : 0);
        hash = 67 * hash + (this.surname != null ? this.surname.hashCode() : 0);
        hash = 67 * hash + (this.lastName != null ? this.lastName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PersonName other = (PersonName) obj;
        if ((this.firstName == null) ? (other.firstName != null) : !this.firstName.equals(other.firstName)) {
            return false;
        }
        if ((this.surname == null) ? (other.surname != null) : !this.surname.equals(other.surname)) {
            return false;
        }
        if ((this.lastName == null) ? (other.lastName != null) : !this.lastName.equals(other.lastName)) {
            return false;
        }
        return true;
    }
    
    

}
