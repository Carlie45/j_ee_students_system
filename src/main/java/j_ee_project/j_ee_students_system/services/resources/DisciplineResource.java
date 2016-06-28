package j_ee_project.j_ee_students_system.services.resources;

import j_ee_project.j_ee_students_system.entities.Discipline;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Lightning
 */
@XmlRootElement
public class DisciplineResource {

    private Long id;
    private String disciplineName;
    private SpecialityResource specialityResource;

    public DisciplineResource(Long id, String disciplineName, SpecialityResource specialityResource) {
        this.id = id;
        this.disciplineName = disciplineName;
        this.specialityResource = specialityResource;
    }
    
    public DisciplineResource(Discipline discipline) {
        this.id = discipline.getId();
        this.disciplineName = discipline.getDisciplineName();
    }
    
     public DisciplineResource(Long id, String disciplineName) {
        this.id = id;
        this.disciplineName = disciplineName;
    }
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDisciplineName() {
        return disciplineName;
    }

    public void setDisciplineName(String disciplineName) {
        this.disciplineName = disciplineName;
    }

    public SpecialityResource getSpecialityResource() {
        return specialityResource;
    }

    public void setSpecialityResource(SpecialityResource specialityResource) {
        this.specialityResource = specialityResource;
    }
    
   

}
