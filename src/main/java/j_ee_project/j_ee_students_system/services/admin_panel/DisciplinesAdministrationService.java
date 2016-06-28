package j_ee_project.j_ee_students_system.services.admin_panel;

import j_ee_project.j_ee_students_system.data_management.DisciplineDataManager;
import j_ee_project.j_ee_students_system.data_management.SpecialityDataManager;
import j_ee_project.j_ee_students_system.data_management.UserDataManager;
import j_ee_project.j_ee_students_system.entities.Discipline;
import j_ee_project.j_ee_students_system.entities.Speciality;
import j_ee_project.j_ee_students_system.security.SystemSecurityManager;
import j_ee_project.j_ee_students_system.security.UserSessionData;
import j_ee_project.j_ee_students_system.services.resources.DisciplineResource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 * REST Web Service
 *
 * @author Lightning
 */
@Path("admin/disciplines")
@Stateless
public class DisciplinesAdministrationService {

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
    public DisciplinesAdministrationService() {
    }

    @POST
    @Path("create_discipline")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createDiscipline(@Valid @Size(min = 5, max = Discipline.MAX_DISCIPLINE_NAME_SIZE, message = "{j_ee_project.j_ee_students_system.security.validation.ValidDisciplineTitle}") @FormParam("disciplineName") String disciplineName, @FormParam("disciplineSpeciality") Long disciplineSpeciality) {
        if (!systemSecurityManager.isAuthorized(userSessionData, "create__disciplines")) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
             
        disciplineName =  StringEscapeUtils.escapeHtml4(disciplineName);
        Speciality speciality = specialityDataManager.find(disciplineSpeciality);
        Discipline discipline = new Discipline(disciplineName, speciality);        
        disciplineDataManager.create(discipline);
        speciality.addDiscipline(discipline);
        specialityDataManager.edit(speciality);
        return Response.ok().build();
    }
       
    
    @POST
    @Path("update_discipline")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateDiscipline(
            @FormParam("disciplineId") Long disciplineId, 
            @Size(min = Discipline.MIN_DISCIPLINE_NAME_SIZE, max = Discipline.MAX_DISCIPLINE_NAME_SIZE, message = "{j_ee_project.j_ee_students_system.security.validation.ValidDisciplineTitle}") @FormParam("disciplineName") String disciplineName,
            @FormParam("disciplineSpeciality") Long specialityId
    ) {
        if (!systemSecurityManager.isAuthorized(userSessionData, "update__disciplines")) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        
        disciplineName = StringEscapeUtils.escapeHtml4(disciplineName);
        Discipline discipline = disciplineDataManager.find(disciplineId);
        discipline.setDisciplineName(disciplineName);
        Speciality speciality = specialityDataManager.find(specialityId);
        discipline.setSpeciality(speciality);  
        
        return Response.ok().build();
    }

}
