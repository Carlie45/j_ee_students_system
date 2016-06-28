package j_ee_project.j_ee_students_system.services.resources;

import j_ee_project.j_ee_students_system.entities.UserRole;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Lightning
 */
@XmlRootElement
public class UserRoleResource {
    private String roleName;
    private String roleTitle;

    public UserRoleResource(String roleName, String roleTitle) {
        this.roleName = roleName;
        this.roleTitle = roleTitle;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleTitle() {
        return roleTitle;
    }

    public void setRoleTitle(String roleTitle) {
        this.roleTitle = roleTitle;
    }

   

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + (this.roleName != null ? this.roleName.hashCode() : 0);
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
        final UserRoleResource other = (UserRoleResource) obj;
        if ((this.roleName == null) ? (other.roleName != null) : !this.roleName.equals(other.roleName)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "UserRoleResource{" + "roleName=" + roleName + '}';
    }
    
}
