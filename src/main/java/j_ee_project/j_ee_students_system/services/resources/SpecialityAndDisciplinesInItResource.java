package j_ee_project.j_ee_students_system.services.resources;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Lightning
 */
@XmlRootElement
public class SpecialityAndDisciplinesInItResource {

    private Long id;
    private String specialityName;
    private DisciplineResource[] disciplines;

    public SpecialityAndDisciplinesInItResource(Long id, String specialityName, DisciplineResource[] disciplines) {
        this.id = id;
        this.specialityName = specialityName;
        this.disciplines = disciplines;
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

    public DisciplineResource[] getDisciplines() {
        return disciplines;
    }

    public void setDisciplines(DisciplineResource[] disciplines) {
        this.disciplines = disciplines;
    }
    
    

}
