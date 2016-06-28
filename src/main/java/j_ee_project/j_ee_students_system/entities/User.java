/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package j_ee_project.j_ee_students_system.entities;

import j_ee_project.j_ee_students_system.entities.embeddable.PersonName;
import j_ee_project.j_ee_students_system.security.validation.ValidUsername;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.validator.constraints.Email;

/**
 *
 * @author Lightning
 */
@Entity
@Table(name = "s_users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", length = User.MAX_USER_TYPE_SIZE)
@DiscriminatorValue(value = User.UserTypes.USER)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
   
    @Column(name = "username", unique = true, nullable = false, length = MAX_USERNAME_SIZE)
    private String username;

    @Column(name = "password", nullable = false, length = MAX_PASSWORD_SIZE)   
    private String password;
     
    @Column(name = "email", unique = true, nullable = false, length = MAX_EMAIL_SIZE)    
    private String email;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "s_users_and_users_roles_assigned", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_name"))    
    private Set<UserRole> roles;
    
    @Embedded
    private PersonName personName;

    @Column(name = "is_activated", nullable = false)
    private boolean isActivated;

    @Transient
    final public static int MIN_USERNAME_SIZE = 5;   
    @Transient
    final public static int MIN_PASSWORD_SIZE = 5;
     @Transient
    final public static int MIN_EMAIL_SIZE = 5;
    @Transient
    final public static int MAX_USERNAME_SIZE = 255;
    @Transient
    final public static int MAX_PASSWORD_SIZE = 255;
    @Transient
    final public static int MAX_EMAIL_SIZE = 255;
    @Transient
    final public static int MAX_USER_TYPE_SIZE = 31;
    @Transient
    final public static int NUMBER_OF_USER_TYPES = 3;

    final static public class UserTypes {

        final public static String USER = "s_users";
        final public static String STUDENT = "s_students";
        final public static String LECTURER = "s_lecturers";
    }

    
    public User() {
    }

    public User(String username, PersonName personName) {
        this.username = username;
        this.personName = personName;
    }

    public User(String username, String password, String email, PersonName personName, boolean isActivated) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.personName = personName;
        this.isActivated = isActivated;
    }

    public User(String username, String password, String email, Set<UserRole> roles, PersonName personName, boolean isActivated) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.roles = roles;
        this.personName = personName;
        this.isActivated = isActivated;
    }

    public Long getId() {
        return id;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }

    public PersonName getPersonName() {
        return personName;
    }

    public void setPersonName(PersonName personName) {
        this.personName = personName;
    }

    public boolean isActivated() {
        return isActivated;
    }

    public void setIsActivated(boolean isActivated) {
        this.isActivated = isActivated;
    }

  
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + (this.username != null ? this.username.hashCode() : 0);
        hash = 53 * hash + (this.email != null ? this.email.hashCode() : 0);
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
        final User other = (User) obj;
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
        return "User{" + "id=" + id + ", username=" + username + ", password=" + password + ", email=" + email + ", role=" + roles + ", personName=" + personName + ", isActivated=" + isActivated + '}';
    } 

}
