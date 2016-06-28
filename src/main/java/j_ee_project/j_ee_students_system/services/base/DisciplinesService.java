package j_ee_project.j_ee_students_system.services.base;

import j_ee_project.j_ee_students_system.data_management.DisciplineDataManager;
import j_ee_project.j_ee_students_system.data_management.SpecialityDataManager;
import j_ee_project.j_ee_students_system.data_management.UserDataManager;
import j_ee_project.j_ee_students_system.entities.Discipline;
import j_ee_project.j_ee_students_system.entities.Speciality;
import j_ee_project.j_ee_students_system.security.SystemSecurityManager;
import j_ee_project.j_ee_students_system.security.UserSessionData;
import j_ee_project.j_ee_students_system.services.resources.DisciplineResource;
import j_ee_project.j_ee_students_system.services.resources.SpecialityResource;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author Lightning
 */
@Path("disciplines")
@Stateless
public class DisciplinesService {

    @EJB
    UserDataManager userDataManager;
    @EJB
    DisciplineDataManager disciplineDataManager;
    @EJB
    SpecialityDataManager specialityDataManager;
    @EJB
    SystemSecurityManager systemSecurityManager;
    @Inject
    UserSessionData userSessionData;

    /**
     * Creates a new instance of DisciplinesAdministrationService
     */
    public DisciplinesService() {
    }

    @GET
    @Path("get_disciplines")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDisciplines() {
        if (!systemSecurityManager.isAuthorized(userSessionData, "read__disciplines")) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        List<Discipline> disciplines = disciplineDataManager.findAll();
        DisciplineResource[] disciplinesResources = new DisciplineResource[disciplines.size()];
        int i = 0;
        for (Discipline discipline : disciplines) {
            Speciality speciality = discipline.getSpeciality();
            disciplinesResources[i++] = new DisciplineResource(
                    discipline.getId(), discipline.getDisciplineName(), new SpecialityResource(speciality));
        }

        return Response.ok().entity(disciplinesResources).build();

    }
    @GET
    @Path("get_discipline_by_id/{disciplineId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDisciplineById(@PathParam("disciplineId") Long disciplineId) {
           if (!systemSecurityManager.isAuthorized(userSessionData, "read__disciplines")) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        Discipline discipline = disciplineDataManager.find(disciplineId); 
        Speciality speciality = discipline.getSpeciality();
        DisciplineResource disciplineResource = new DisciplineResource(
                discipline.getId(), discipline.getDisciplineName(), new SpecialityResource(speciality));
        
        
        return Response.ok().entity(disciplineResource).build();
    }
    
    @POST
    @Path("create_discipline")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createDiscipline(@Valid @Size(min = 5, max=Discipline.MAX_DISCIPLINE_NAME_SIZE, message = "{j_ee_project.j_ee_students_system.security.validation.ValidDisciplineTitle}") @FormParam("disciplineName") String disciplineName, @FormParam("disciplineSpeciality") Long disciplineSpeciality) {
        if (!systemSecurityManager.isAuthorized(userSessionData, "create__disciplines")) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        Speciality speciality = specialityDataManager.find(disciplineSpeciality);
        Discipline discipline = new Discipline(disciplineName, speciality);
        disciplineDataManager.create(discipline);
        return Response.ok().build();
    }
}
