package j_ee_project.j_ee_students_system.services.base;

import j_ee_project.j_ee_students_system.data_management.DegreeDataManager;
import j_ee_project.j_ee_students_system.data_management.UserDataManager;
import j_ee_project.j_ee_students_system.entities.Degree;
import j_ee_project.j_ee_students_system.entities.User;
import j_ee_project.j_ee_students_system.security.SystemSecurityManager;
import j_ee_project.j_ee_students_system.security.UserSessionData;
import j_ee_project.j_ee_students_system.security.validation.ValidUsername;
import j_ee_project.j_ee_students_system.services.ValidationErrorResponse;
import j_ee_project.j_ee_students_system.services.resources.DegreeResource;
import j_ee_project.j_ee_students_system.services.resources.UserNotEmbeddedResource;
import j_ee_project.j_ee_students_system.services.resources.UserResource;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.hibernate.validator.constraints.Email;

/**
 * Users Web Service
 *
 * @author Lightning
 */
@Path("users")
@Stateless
public class UsersService {

    @EJB
    UserDataManager userDataManager;
    @EJB
    DegreeDataManager degreeDataManager;
    @EJB
    SystemSecurityManager systemSecurityManager;
    @Inject
    UserSessionData userSessionData;

    /**
     * Creates a new instance of UsersService
     */
    public UsersService() {
    }

    @GET
    @Path("is_user_authenticated")
    @Produces(MediaType.APPLICATION_JSON)
    public Response isUserAuthenticated() {
        if (systemSecurityManager.isAuthenticated(userSessionData)) {
            User user = userDataManager.find(userSessionData.getUserId());
            return Response.ok().entity(new UserResource(user)).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @GET
    @Path("logout")
    @Produces(MediaType.APPLICATION_JSON)
    public Response logout() {
        if (systemSecurityManager.logout(userSessionData)) {
            return Response.ok().build();
        }
        return Response.serverError().build();
    }

    @GET
    @Path("is_user_authorized/{right_name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response isUserAuthorized(@PathParam("right_name") String rightName) {
        if (systemSecurityManager.isAuthorized(userSessionData, rightName)) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@Valid @ValidUsername @FormParam("username") String username, @FormParam("password") String password) {
        if (!systemSecurityManager.isAuthenticated(userSessionData) && systemSecurityManager.authenticate(username, password, userSessionData)) {
            User user = userDataManager.find(userSessionData.getUserId());
            return Response.ok().entity(new UserResource(user)).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @POST
    @Path("update_profile")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateProfile(
            @Valid @Email @FormParam("email") String email,
            @Valid @Size(max = User.MAX_PASSWORD_SIZE, message = "{j_ee_project.j_ee_students_system.security.validation.ValidPassword}") @FormParam("oldPassword") String oldPassword,
            @Valid @Size(max = User.MAX_PASSWORD_SIZE, message = "{j_ee_project.j_ee_students_system.security.validation.ValidPassword}") @FormParam("password") String password,
            @Valid @Size(max = User.MAX_PASSWORD_SIZE, message = "{j_ee_project.j_ee_students_system.security.validation.ValidPassword}") @FormParam("passwordRepeat") String passwordRepeat
    ) {
        User user = userDataManager.find(userSessionData.getUserId());
        String userPassword = user.getPassword();
        try {
            List<String> errors = new ArrayList();
            oldPassword = systemSecurityManager.calculatePasswordHash(oldPassword);
            if (oldPassword.equals(userPassword)) {
                user.setEmail(email);
                if (!password.isEmpty()) {
                    if (password.equals(passwordRepeat) && password.length() >= User.MIN_PASSWORD_SIZE) {
                        user.setPassword(systemSecurityManager.calculatePasswordHash(password));
                    } else {
                        errors.add("Въведените пароли за обновяване не съвпадат или са с дължина под 5 символа!");
                        return Response.status(Response.Status.BAD_REQUEST).entity(new ValidationErrorResponse(errors)).build();
                    }
                }
                userDataManager.edit(user);
                return Response.ok().build();
            } else {
                errors.add("Грешна парола!");
                return Response.status(Response.Status.BAD_REQUEST).entity(new ValidationErrorResponse(errors)).build();
            }
        } catch (NoSuchAlgorithmException ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GET
    @Path("get_users/{page}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get_users_page(@PathParam("page") Integer page) {
        if (page < 1) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        int perPage = 10;
        int startBegin = (page - 1) * perPage;
        int StratEnd = startBegin + perPage;
        List<User> users = userDataManager.findRange(new int[]{startBegin, StratEnd});
        UserNotEmbeddedResource[] usersResources = new UserNotEmbeddedResource[users.size()];
        int usersSize = users.size();
        for (int i = 0; i != usersSize; ++i) {
            usersResources[i] = new UserNotEmbeddedResource(users.get(i));
        }
        return Response.ok().entity(usersResources).build();
    }

    @GET
    @Path("get_users/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get_users() {
        if (!systemSecurityManager.isAuthorized(userSessionData, "read__users")) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        List<User> users = userDataManager.findAll();
        UserNotEmbeddedResource[] usersResources = new UserNotEmbeddedResource[users.size()];
        int usersSize = users.size();
        for (int i = 0; i != usersSize; ++i) {
            usersResources[i] = new UserNotEmbeddedResource(users.get(i));
        }
        return Response.ok().entity(usersResources).build();
    }

    @GET
    @Path("count_users")
    @Produces(MediaType.APPLICATION_JSON)
    public Response countUsers() {
        if (!systemSecurityManager.isAuthorized(userSessionData, "read__users")) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        return Response.ok().entity(new Integer[]{userDataManager.count()}).build();
    }

    @GET
    @Path("get_degrees")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDegrees() {
        if (!systemSecurityManager.isAuthorized(userSessionData, "read__users")) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        List<Degree> degrees = degreeDataManager.findAll();
        DegreeResource[] degreesResources = new DegreeResource[degrees.size()];
        int i = 0;
        for(Degree degree : degrees){
            degreesResources[i++] = new DegreeResource(degree.getId(), degree.getDegreeName());
        }
        
        return Response.ok().entity(degreesResources).build();
    }

}
