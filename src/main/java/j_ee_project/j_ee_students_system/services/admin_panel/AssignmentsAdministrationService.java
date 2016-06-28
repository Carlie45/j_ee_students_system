package j_ee_project.j_ee_students_system.services.admin_panel;

import j_ee_project.j_ee_students_system.data_management.AssignmentDataManager;
import j_ee_project.j_ee_students_system.data_management.DisciplineDataManager;
import j_ee_project.j_ee_students_system.data_management.SpecialityDataManager;
import j_ee_project.j_ee_students_system.data_management.UserDataManager;
import j_ee_project.j_ee_students_system.data_management.UserRoleDataManager;
import j_ee_project.j_ee_students_system.entities.Assignment;
import j_ee_project.j_ee_students_system.entities.Discipline;
import j_ee_project.j_ee_students_system.security.SystemSecurityManager;
import j_ee_project.j_ee_students_system.security.UserSessionData;
import j_ee_project.j_ee_students_system.services.ValidationErrorResponse;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 * REST Web Service
 *
 * @author Lightning
 */
@Path("admin/assignments")
@Stateless
public class AssignmentsAdministrationService {

    @EJB
    UserDataManager userDataManager;
    @EJB
    UserRoleDataManager userRoleDataManager;
    @EJB
    SpecialityDataManager specialityDataManager;
    @EJB
    DisciplineDataManager disciplineDataManager;
    @EJB
    AssignmentDataManager assignmentDataManager;
    @EJB
    SystemSecurityManager systemSecurityManager;
    @Inject
    UserSessionData userSessionData;

    /**
     * Creates a new instance of AssignmentsAdministrationService
     */
    public AssignmentsAdministrationService() {
    }

    @POST
    @Path("create_assignment")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAssignment(
            @Valid @Size(min = Assignment.MIN_TITLE_SIZE, max = Assignment.MAX_TITLE_SIZE, message = "{j_ee_project.j_ee_students_system.security.validation.ValidAssignmentTitle}") @FormParam("title") String title,
            @FormParam("discipline") Long discipline,
            @FormParam("uploadedFile") String uploadedFile,
            @FormParam("description") String description,
            @FormParam("startTime") String startTime,
            @FormParam("endTime") String endTime
    ) {
        if (systemSecurityManager.isAuthorized(userSessionData, "create__assignments")) {
            title = StringEscapeUtils.escapeHtml4(title);
            uploadedFile = StringEscapeUtils.escapeHtml4(uploadedFile);
            description = StringEscapeUtils.escapeHtml4(description);
            startTime = StringEscapeUtils.escapeHtml4(startTime);
            endTime = StringEscapeUtils.escapeHtml4(endTime);

            List<String> errors = new ArrayList();

            DateFormat format = new SimpleDateFormat("dd.MM.yy");
            try {
                Date startTimeDate = format.parse(startTime);
                Date endTimeDate = format.parse(endTime);
                Discipline assignmentDiscipline = disciplineDataManager.find(discipline);
                Assignment assignment = new Assignment(title, uploadedFile, description, startTimeDate, endTimeDate, assignmentDiscipline);
                assignmentDataManager.create(assignment);
                return Response.ok().build();

            } catch (ParseException ex) {
                errors.add("Невалидна дата!");
                return Response.status(Response.Status.BAD_REQUEST).entity(new ValidationErrorResponse(errors)).build();
            }
        }

        return Response.status(Response.Status.FORBIDDEN).build();

    }

}
