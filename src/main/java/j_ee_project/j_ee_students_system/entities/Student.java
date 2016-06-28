package j_ee_project.j_ee_students_system.entities;

import j_ee_project.j_ee_students_system.entities.embeddable.PersonName;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Lightning
 */
@Entity
@XmlRootElement
@DiscriminatorValue(value = User.UserTypes.STUDENT)
public class Student extends User implements Serializable {

    private static final long serialVersionUID = 1L;  

    @Column(name = "faculty_number", unique = true, nullable = true, length = MAX_FACULTY_NUMBER_SIZE)
    private String facultyNumber;
    
    @ManyToMany(mappedBy = "students")
    private Set<Speciality> specialities;
    
    @OneToMany(mappedBy = "submittedBy")   
    private Set<AssignmentSolution> assignmentsSolutionsSubmitted;
    
    @Transient
    final public static int MAX_FACULTY_NUMBER_SIZE = 20;

    public Student() {
        super();
    }

    public Student(String facultyNumber, Set<Speciality> specialities, String username, String password, String email, Set<UserRole> roles, PersonName personName, boolean isActivated) {
        super(username, password, email, roles, personName, isActivated);
        this.facultyNumber = facultyNumber;
        this.specialities = specialities;
    }

   

    public String getFacultyNumber() {
        return facultyNumber;
    }

    public void setFacultyNumber(String facultyNumber) {
        this.facultyNumber = facultyNumber;
    }

    public Set<Speciality> getSpecialities() {
        return specialities;
    }

    public void setSpecialities(Set<Speciality> specialities) {
        this.specialities = specialities;
    }

    public Set<AssignmentSolution> getAssignmentsSolutionsSubmitted() {
        return assignmentsSolutionsSubmitted;
    }

    public void setAssignmentsSolutionsSubmitted(Set<AssignmentSolution> assignmentsSolutionsSubmitted) {
        this.assignmentsSolutionsSubmitted = assignmentsSolutionsSubmitted;
    } 

    
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (this.facultyNumber != null ? this.facultyNumber.hashCode() : 0);
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
        final Student other = (Student) obj;
        if ((this.facultyNumber == null) ? (other.facultyNumber != null) : !this.facultyNumber.equals(other.facultyNumber)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Student{" + "facultyNumber=" + facultyNumber + ", specialities=" + specialities + '}';
    }

   
    
}
