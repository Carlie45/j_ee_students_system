package j_ee_project.j_ee_students_system.services.resources;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Lightning
 */
@XmlRootElement
public class AssignmentsByDisciplineAndSpecialityResource {
    
    private SpecialityResource specialityResource;
    private DisciplineResource disciplineResource;
    private AssignmentResource[] assignmentsResources;

    public AssignmentsByDisciplineAndSpecialityResource(SpecialityResource specialityResource, DisciplineResource disciplineResource, AssignmentResource[] assignmentsResources) {
        this.specialityResource = specialityResource;
        this.disciplineResource = disciplineResource;
        this.assignmentsResources = assignmentsResources;
    }

    public SpecialityResource getSpecialityResource() {
        return specialityResource;
    }

    public void setSpecialityResource(SpecialityResource specialityResource) {
        this.specialityResource = specialityResource;
    }

    public DisciplineResource getDisciplineResource() {
        return disciplineResource;
    }

    public void setDisciplineResource(DisciplineResource disciplineResource) {
        this.disciplineResource = disciplineResource;
    }

    public AssignmentResource[] getAssignmentsResources() {
        return assignmentsResources;
    }

    public void setAssignmentsResources(AssignmentResource[] assignmentsResources) {
        this.assignmentsResources = assignmentsResources;
    }
    
    
}
