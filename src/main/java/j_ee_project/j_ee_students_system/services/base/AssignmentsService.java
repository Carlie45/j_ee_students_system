package j_ee_project.j_ee_students_system.services.base;

import j_ee_project.j_ee_students_system.data_management.AssignmentDataManager;
import j_ee_project.j_ee_students_system.data_management.DisciplineDataManager;
import j_ee_project.j_ee_students_system.data_management.SpecialityDataManager;
import j_ee_project.j_ee_students_system.data_management.UserDataManager;
import j_ee_project.j_ee_students_system.data_management.UserRoleDataManager;
import j_ee_project.j_ee_students_system.entities.Assignment;
import j_ee_project.j_ee_students_system.entities.Discipline;
import j_ee_project.j_ee_students_system.security.SystemSecurityManager;
import j_ee_project.j_ee_students_system.security.UserSessionData;
import j_ee_project.j_ee_students_system.services.resources.AssignmentResource;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author Lightning
 */
@Path("assignments")
@Stateless
public class AssignmentsService {

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
     * Creates a new instance of AssignmentsService
     */
    public AssignmentsService() {
    }

    @GET
    @Path("get_assignments_by_discipline_id/{disciplineId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAssignmentsByDisciplineId(@PathParam("disciplineId") Long disciplineId) {
        if (!systemSecurityManager.isAuthorized(userSessionData, "read__assignments")) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        Discipline discipline = disciplineDataManager.find(disciplineId);

        Set<Assignment> assignments = discipline.getAssignments();
        AssignmentResource[] assignmentsResources = new AssignmentResource[assignments.size()];
        int i = 0;
        for (Assignment assignment : assignments) {
            assignmentsResources[i++] = new AssignmentResource(assignment);
        }
        return Response.ok().entity(assignmentsResources).build();
    }

    @GET
    @Path("get_assignment/{assignmentId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAssignment(@PathParam("assignmentId") Long assignmentId) {
        if (!systemSecurityManager.isAuthorized(userSessionData, "read__assignments")) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        Assignment assignment = assignmentDataManager.find(assignmentId);

        return Response.ok().entity(new AssignmentResource(assignment)).build();
    }

}
