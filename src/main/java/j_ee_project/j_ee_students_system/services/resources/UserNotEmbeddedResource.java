package j_ee_project.j_ee_students_system.services.resources;

import j_ee_project.j_ee_students_system.entities.User;
import j_ee_project.j_ee_students_system.entities.embeddable.PersonName;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Lightning
 */
@XmlRootElement
public class UserNotEmbeddedResource {

    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String surname;
    private String lastName;
    private boolean isActivated;

    public UserNotEmbeddedResource(String username, String email, PersonName personName) {
        this.username = username;
        this.email = email;
        this.firstName = personName.getFirstName();
        this.surname = personName.getSurname();
        this.lastName = personName.getLastName();
    }

    public UserNotEmbeddedResource(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        PersonName personName = user.getPersonName();
        this.firstName = personName.getFirstName();
        this.surname = personName.getSurname();
        this.lastName = personName.getLastName();
        this.isActivated = user.isActivated();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

   

    public boolean isIsActivated() {
        return isActivated;
    }

    public void setIsActivated(boolean isActivated) {
        this.isActivated = isActivated;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (this.username != null ? this.username.hashCode() : 0);
        hash = 83 * hash + (this.email != null ? this.email.hashCode() : 0);
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
        final UserNotEmbeddedResource other = (UserNotEmbeddedResource) obj;
        if ((this.username == null) ? (other.username != null) : !this.username.equals(other.username)) {
            return false;
        }
        if ((this.email == null) ? (other.email != null) : !this.email.equals(other.email)) {
            return false;
        }
        return true;
    }

   

}
