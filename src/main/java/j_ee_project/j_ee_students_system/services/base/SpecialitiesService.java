package j_ee_project.j_ee_students_system.services.base;

import j_ee_project.j_ee_students_system.data_management.SpecialityDataManager;
import j_ee_project.j_ee_students_system.data_management.UserDataManager;
import j_ee_project.j_ee_students_system.entities.Discipline;
import j_ee_project.j_ee_students_system.entities.Speciality;
import j_ee_project.j_ee_students_system.security.SystemSecurityManager;
import j_ee_project.j_ee_students_system.security.UserSessionData;
import j_ee_project.j_ee_students_system.services.resources.DisciplineResource;
import j_ee_project.j_ee_students_system.services.resources.SpecialityAndDisciplinesInItResource;
import j_ee_project.j_ee_students_system.services.resources.SpecialityResource;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.inject.Inject;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author Lightning
 */
@Path("specialities")
@Stateless
public class SpecialitiesService {

    @EJB
    UserDataManager userDataManager;
    @EJB
    SpecialityDataManager specialityDataManager;
    @EJB
    SystemSecurityManager systemSecurityManager;
    @Inject
    UserSessionData userSessionData;

    /**
     * Creates a new instance of SpecialitiesAdministrationService
     */
    public SpecialitiesService() {
    }

    @GET
    @Path("get_specialities")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSpecialities() {
        if (systemSecurityManager.isAuthorized(userSessionData, "read__specialities")) {
            List<Speciality> specialities = specialityDataManager.findAll();
            SpecialityResource[] specialitiesResources = new SpecialityResource[specialities.size()];
            int i = 0;
            for (Speciality speciality : specialities) {
                specialitiesResources[i++] = new SpecialityResource(speciality.getId(), speciality.getSpecialityName());
            }

            return Response.ok().entity(specialitiesResources).build();
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }
    
    @GET
    @Path("get_specialities_by_id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSpecialityById(@PathParam("id") Long id) {
        if (systemSecurityManager.isAuthorized(userSessionData, "read__specialities")) {
            Speciality speciality = specialityDataManager.find(id);
            return Response.ok().entity(new SpecialityResource(speciality.getId(), speciality.getSpecialityName())).build();
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }
    
     @GET
    @Path("get_specialities_and_disciplines_in_them")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSpecialitiesAndDisciplinesInThem() {
        if (systemSecurityManager.isAuthorized(userSessionData, "read__specialities")) {
            List<Speciality> specialities = specialityDataManager.findAll();
            SpecialityAndDisciplinesInItResource[] specialitiesResources = 
                    new SpecialityAndDisciplinesInItResource[specialities.size()];
            int i = 0;
            for (Speciality speciality : specialities) {
                Set<Discipline> disciplines = speciality.getDisciplines();
                DisciplineResource[] disciplinesResources = new DisciplineResource[disciplines.size()];
                int j = 0;
                for (Discipline discipline : disciplines){
                    disciplinesResources[j++] = 
                            new DisciplineResource(discipline.getId(), discipline.getDisciplineName());
                }
                
                specialitiesResources[i++] =
                        new SpecialityAndDisciplinesInItResource(speciality.getId(), speciality.getSpecialityName(), disciplinesResources);
            }

            return Response.ok().entity(specialitiesResources).build();
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }
}
