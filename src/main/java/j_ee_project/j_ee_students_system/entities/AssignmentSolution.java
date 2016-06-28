package j_ee_project.j_ee_students_system.entities;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Lightning
 */
@Entity
@XmlRootElement
@Table(name = "s_assignments_solution")
public class AssignmentSolution implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "assignment_id", nullable = false)
    private Assignment assignment;

    @ManyToOne
    @JoinColumn(name = "submitted_by", nullable = false)
    private User submittedBy;

    @ManyToOne
    @JoinColumn(name = "graded_by", nullable = true)
    private Lecturer gradedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_time", nullable = true)
    private Date creationTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "time_submitted", nullable = false)
    private Date timeSubmitted;

    @Lob
    @Column(name = "solution_text", nullable = true)
    private String solutionText;

    @Column(name = "fileName", unique = true, nullable = false, length = MAX_FILE_NAME_SIZE)
    private String fileName;

    @Column(name = "mark", nullable = true)
    private Double mark;

    final public static int MAX_FILE_NAME_SIZE = 250;

    public AssignmentSolution() {
        this.creationTime = Calendar.getInstance().getTime();
    }

    public AssignmentSolution(Assignment assignment, User submittedBy, String fileName) {
        this.assignment = assignment;
        this.submittedBy = submittedBy;
        this.timeSubmitted = Calendar.getInstance().getTime();
        this.fileName = fileName;
    }

    
    
    

    public AssignmentSolution(Assignment assignment, User submittedBy, Lecturer gradedBy, Date timeSubmitted, String solutionText, String fileName, Double mark) {
        this.assignment = assignment;
        this.submittedBy = submittedBy;
        this.gradedBy = gradedBy;
        this.timeSubmitted = timeSubmitted;
        this.solutionText = solutionText;
        this.fileName = fileName;
        this.mark = mark;
        this.creationTime = Calendar.getInstance().getTime();
    }

    public Long getId() {
        return id;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public User getSubmittedBy() {
        return submittedBy;
    }

    public void setSubmittedBy(User submittedBy) {
        this.submittedBy = submittedBy;
    }

    public Lecturer getGradedBy() {
        return gradedBy;
    }

    public void setGradedBy(Lecturer gradedBy) {
        this.gradedBy = gradedBy;
    }

    public Date getTimeSubmitted() {
        return timeSubmitted;
    }

    public Date getCreationTime() {
        return creationTime;
    }   
    

    public void setTimeSubmitted(Date timeSubmitted) {
        this.timeSubmitted = timeSubmitted;
    }

    public String getSolutionText() {
        return solutionText;
    }

    public void setSolutionText(String solutionText) {
        this.solutionText = solutionText;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Double getMark() {
        return mark;
    }

    public void setMark(Double mark) {
        this.mark = mark;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 89 * hash + (this.assignment != null ? this.assignment.hashCode() : 0);
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
        final AssignmentSolution other = (AssignmentSolution) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if (this.assignment != other.assignment && (this.assignment == null || !this.assignment.equals(other.assignment))) {
            return false;
        }
        return true;
    }

}
