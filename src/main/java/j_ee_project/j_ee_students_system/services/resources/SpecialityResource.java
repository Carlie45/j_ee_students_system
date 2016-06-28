package j_ee_project.j_ee_students_system.services.resources;

import j_ee_project.j_ee_students_system.entities.Speciality;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Lightning
 */
@XmlRootElement
public class SpecialityResource {

    private Long id;
    private String specialityName;

    public SpecialityResource(Long id, String specialityName) {
        this.id = id;
        this.specialityName = specialityName;
    }
    
    public SpecialityResource(Speciality speciality) {
        this.id = speciality.getId();
        this.specialityName = speciality.getSpecialityName();
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSpecialityName() {
        return specialityName;
    }

    public void setSpecialityName(String specialityName) {
        this.specialityName = specialityName;
    }
    
    

}
