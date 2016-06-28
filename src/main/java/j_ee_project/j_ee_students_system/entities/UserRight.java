package j_ee_project.j_ee_students_system.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Lightning
 */
@Entity
@XmlRootElement
@Table(name = "s_users_rights")
public class UserRight implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "right_name")
    private String rightName;
    
    @ManyToMany
    @JoinTable(name = "s_users_rights_and_users_roles", joinColumns = @JoinColumn(name = "right_name"), inverseJoinColumns = @JoinColumn(name = "role_name"))
    private Set<UserRole> rolesAllowed;

    public UserRight() {
    }

    public UserRight(String rightName) {
        this.rightName = rightName;
    }

    public String getRightName() {
        return rightName;
    }

    public void setRightName(String rightName) {
        this.rightName = rightName;
    }

    public Set<UserRole> getRolesAllowed() {
        return rolesAllowed;
    }

    public void setRolesAllowed(Set<UserRole> rolesAllowed) {
        this.rolesAllowed = rolesAllowed;
    }

   
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.rightName != null ? this.rightName.hashCode() : 0);
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
        final UserRight other = (UserRight) obj;
        if ((this.rightName == null) ? (other.rightName != null) : !this.rightName.equals(other.rightName)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "UserRight{" + "rightName=" + rightName + '}';
    }
    
    

    
}
