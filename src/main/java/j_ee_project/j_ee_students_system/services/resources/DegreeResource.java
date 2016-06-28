package j_ee_project.j_ee_students_system.services.resources;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Lightning
 */
@XmlRootElement
public class DegreeResource {
    private Long id;
    private String degreeName;

    public DegreeResource(Long id, String degreeName) {
        this.id = id;
        this.degreeName = degreeName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

   

    public String getDegreeName() {
        return degreeName;
    }

    public void setDegreeName(String degreeName) {
        this.degreeName = degreeName;
    }
    
}
