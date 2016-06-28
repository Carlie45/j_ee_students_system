package j_ee_project.j_ee_students_system.entities;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Lightning
 */
@Entity
@XmlRootElement
@Table(name = "s_assignments")
public class Assignment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false, length = MAX_TITLE_SIZE)
    private String title;
    
    @Column(name = "attached_file", nullable = false, length = MAX_TITLE_SIZE)
    private String attachedFile;
    
    @Lob
    @Column(name = "description", nullable = true)
    private String description;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_time", nullable = false)
    private Date creationTime;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_time", nullable = false)
    private Date startTime;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_time", nullable = false)
    private Date endTime;
    
    @ManyToOne
    @JoinColumn(name="discipline_id")
    private Discipline discipline;
    
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="assignment_id")
    private Set<AssignmentSolution> assignmentSolution;
    
    @Transient
    final public static int MIN_TITLE_SIZE = 5;
    final public static int MIN_FILE_NAME_SIZE = 5;   
    final public static int MAX_TITLE_SIZE = 255;
    final public static int MAX_FILE_NAME_SIZE = 255;

    public Assignment() {
         this.creationTime =  Calendar.getInstance().getTime();
    }

    public Assignment(String title, String attachedFile, String description, Date startTime, Date endTime, Discipline discipline) {
        this.title = title;
        this.attachedFile = attachedFile;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.discipline = discipline;      
        this.creationTime =  Calendar.getInstance().getTime();   
    } 
  

    public Long getId() {
        return id;
    }
  
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAttachedFile() {
        return attachedFile;
    }

    public void setAttachedFile(String attachedFile) {
        this.attachedFile = attachedFile;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    
    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    public Set<AssignmentSolution> getAssignmentSolution() {
        return assignmentSolution;
    }
    
    public void addAssignmentSolution(AssignmentSolution assignmentSolution){
        this.assignmentSolution.add(assignmentSolution);
    }

    public void setAssignmentSolution(Set<AssignmentSolution> assignmentSolution) {
        this.assignmentSolution = assignmentSolution;
    }    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 71 * hash + (this.title != null ? this.title.hashCode() : 0);
        hash = 71 * hash + (this.discipline != null ? this.discipline.hashCode() : 0);
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
        final Assignment other = (Assignment) obj;
        if ((this.title == null) ? (other.title != null) : !this.title.equals(other.title)) {
            return false;
        }
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if (this.discipline != other.discipline && (this.discipline == null || !this.discipline.equals(other.discipline))) {
            return false;
        }
        return true;
    }
    
        
}
