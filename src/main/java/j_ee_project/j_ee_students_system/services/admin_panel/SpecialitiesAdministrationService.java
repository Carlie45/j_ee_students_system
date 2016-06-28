package j_ee_project.j_ee_students_system.services.admin_panel;

import j_ee_project.j_ee_students_system.data_management.SpecialityDataManager;
import j_ee_project.j_ee_students_system.data_management.UserDataManager;
import j_ee_project.j_ee_students_system.entities.Speciality;
import j_ee_project.j_ee_students_system.security.SystemSecurityManager;
import j_ee_project.j_ee_students_system.security.UserSessionData;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.inject.Inject;
import javax.validation.constraints.Size;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * REST Web Service
 *
 * @author Lightning
 */
@Path("admin/specialities")
@Stateless
public class SpecialitiesAdministrationService {

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
    public SpecialitiesAdministrationService() {
    }

    @POST
    @Path("create_speciality")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createSpeciality(@Size(min=Speciality.MIN_SPECIALITY_NAME_SIZE, max=Speciality.MAX_SPECIALITY_NAME_SIZE, message = "{j_ee_project.j_ee_students_system.security.validation.ValidSpecialityTitle}") @FormParam("specialityName") String specialityName){
        if (!systemSecurityManager.isAuthorized(userSessionData, "create__specialities")) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        specialityName = StringEscapeUtils.escapeHtml4(specialityName);
        Speciality speciality = new Speciality(specialityName);
        specialityDataManager.create(speciality);
        return Response.ok().build();
    }
    
    @POST
    @Path("update_speciality/{specialityId}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateSpeciality(@PathParam("specialityId") Long specialityId, @Size(min=Speciality.MIN_SPECIALITY_NAME_SIZE, max=Speciality.MAX_SPECIALITY_NAME_SIZE, message = "{j_ee_project.j_ee_students_system.security.validation.ValidSpecialityTitle}") @FormParam("specialityName") String specialityName) {
        if (!systemSecurityManager.isAuthorized(userSessionData, "update__specialities")) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }       
       
        specialityName = StringEscapeUtils.escapeHtml4(specialityName);
        Speciality speciality = specialityDataManager.find(specialityId);
        speciality.setSpecialityName(specialityName);
        specialityDataManager.edit(speciality);
        return Response.ok().build();
    }

}
