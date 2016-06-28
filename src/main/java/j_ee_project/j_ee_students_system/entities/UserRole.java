/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package j_ee_project.j_ee_students_system.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Lightning
 */
@Entity
@Table(name = "s_users_roles_list")
@XmlRootElement
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;      
    
    @Id
    @Column(name = "role_name", unique = true, nullable = false, length = MAX_USER_ROLE_NAME_SIZE)    
    private String roleName;
    
    @Column(name = "role_title", nullable = false, length = MAX_USER_ROLE_NAME_SIZE)    
    private String roleTitle;    
  
    
    final public static int MAX_USER_ROLE_NAME_SIZE = 255;

    public UserRole() {
    }

    public UserRole(String roleName) {
        this.roleName = roleName;
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
        hash = 89 * hash + (this.roleName != null ? this.roleName.hashCode() : 0);
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
        final UserRole other = (UserRole) obj;
        if ((this.roleName == null) ? (other.roleName != null) : !this.roleName.equals(other.roleName)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "UserRole{" + "roleName=" + roleName + '}';
    }
    
    
    public static  boolean isValid(String roleName){
        return (roleName.length() >= 5 &&
                roleName.length() <= UserRole.MAX_USER_ROLE_NAME_SIZE &&
                java.util.regex.Pattern.matches("^([a-z0-9_])+", roleName));
    }


    
}
