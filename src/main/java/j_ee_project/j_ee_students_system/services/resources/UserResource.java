package j_ee_project.j_ee_students_system.services.resources;

import j_ee_project.j_ee_students_system.entities.User;
import j_ee_project.j_ee_students_system.entities.embeddable.PersonName;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Lightning
 */
@XmlRootElement
public class UserResource {

    private Long id;
    private String username;
    private String email;
    private PersonName personName;
    private boolean isActivated;

    public UserResource(String username, String email, PersonName personName) {
        this.username = username;
        this.email = email;
        this.personName = personName;
    }
    
    

    public UserResource(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.personName = user.getPersonName();
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

    public PersonName getPersonName() {
        return personName;
    }

    public void setPersonName(PersonName personName) {
        this.personName = personName;
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
        final UserResource other = (UserResource) obj;
        if ((this.username == null) ? (other.username != null) : !this.username.equals(other.username)) {
            return false;
        }
        if ((this.email == null) ? (other.email != null) : !this.email.equals(other.email)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "UserResource{" + "id=" + id + ", username=" + username + ", email=" + email + ", personName=" + personName + ", isActivated=" + isActivated + '}';
    }
    

}
