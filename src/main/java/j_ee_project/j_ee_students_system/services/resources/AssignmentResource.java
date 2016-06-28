package j_ee_project.j_ee_students_system.services.resources;

import j_ee_project.j_ee_students_system.entities.Assignment;
import j_ee_project.j_ee_students_system.entities.Discipline;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Lightning
 */
@XmlRootElement
public class AssignmentResource {

    private Long id;
    private String title;
    private String attachedFile;
    private String description;
    private String startTime;
    private String endTime;


    public AssignmentResource(Long id, String title, String attachedFile, String description, String startTime, String endTime) {
        this.id = id;
        this.title = title;
        this.attachedFile = attachedFile;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        
    }

    public AssignmentResource(Assignment assignment) {
        this.id = assignment.getId();
        this.title = assignment.getTitle();
        this.description = assignment.getDescription();
        this.attachedFile = assignment.getAttachedFile();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");       
        this.startTime = dateFormat.format(assignment.getStartTime());
        this.endTime = dateFormat.format(assignment.getEndTime());
    }
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAttachedFile() {
        return attachedFile;
    }

    public void setAttachedFile(String attachedFile) {
        this.attachedFile = attachedFile;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

  
    
    
}
