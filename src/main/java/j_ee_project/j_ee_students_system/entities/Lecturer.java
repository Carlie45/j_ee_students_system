package j_ee_project.j_ee_students_system.entities;

import j_ee_project.j_ee_students_system.entities.embeddable.PersonName;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Lightning
 */
@Entity
@XmlRootElement
@DiscriminatorValue(value = User.UserTypes.LECTURER)
public class Lecturer extends User implements Serializable {

    private static final long serialVersionUID = 1L;  
    
    @Column(name = "information", nullable = true, length = MAX_INFORMATION_SIZE)
    private String information;
    
    @ManyToMany
    @JoinTable(name = "s_lecturers_and_their_degrees", joinColumns = @JoinColumn(name = "lecturer_id"), inverseJoinColumns = @JoinColumn(name = "degree_id"))
    private Set<Degree> degrees;
    
    @ManyToMany(mappedBy = "lecturers")   
    private Set<Discipline> disciplines;
    
    @OneToMany(mappedBy = "gradedBy")  
    private Set<AssignmentSolution> assignmentsSolutionsGraded;
    
    @Transient
    final public static int MAX_INFORMATION_SIZE = 255;

    public Lecturer() {
        super();
    }

    public Lecturer(Set<Degree> degrees, Set<Discipline> disciplines, String username, String password, String email, Set<UserRole> roles, PersonName personName, boolean isActivated) {
        super(username, password, email, roles, personName, isActivated);
        this.degrees = degrees;
        this.disciplines = disciplines;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public Set<Degree> getDegrees() {
        return degrees;
    }

    public void setDegrees(Set<Degree> degrees) {
        this.degrees = degrees;
    }

    public Set<Discipline> getDisciplines() {
        return disciplines;
    }

    public void setDisciplines(Set<Discipline> disciplines) {
        this.disciplines = disciplines;
    }

    public Set<AssignmentSolution> getAssignmentsSolutionsGraded() {
        return assignmentsSolutionsGraded;
    }

    public void setAssignmentsSolutionsGraded(Set<AssignmentSolution> assignmentsSolutionsGraded) {
        this.assignmentsSolutionsGraded = assignmentsSolutionsGraded;
    }
    
    


   
    
}
