package j_ee_project.j_ee_students_system.services.admin_panel;

import j_ee_project.j_ee_students_system.data_management.DegreeDataManager;
import j_ee_project.j_ee_students_system.data_management.DisciplineDataManager;
import j_ee_project.j_ee_students_system.data_management.LecturerDataManager;
import j_ee_project.j_ee_students_system.data_management.SpecialityDataManager;
import j_ee_project.j_ee_students_system.data_management.StudentDataManager;
import j_ee_project.j_ee_students_system.data_management.UserDataManager;
import j_ee_project.j_ee_students_system.data_management.UserRoleDataManager;
import j_ee_project.j_ee_students_system.entities.Degree;
import j_ee_project.j_ee_students_system.entities.Discipline;
import j_ee_project.j_ee_students_system.entities.Lecturer;
import j_ee_project.j_ee_students_system.entities.Speciality;
import j_ee_project.j_ee_students_system.entities.Student;
import j_ee_project.j_ee_students_system.entities.User;
import j_ee_project.j_ee_students_system.entities.UserRole;
import j_ee_project.j_ee_students_system.entities.embeddable.PersonName;
import j_ee_project.j_ee_students_system.security.SystemSecurityManager;
import j_ee_project.j_ee_students_system.security.UserSessionData;
import j_ee_project.j_ee_students_system.security.validation.ValidPartOfName;
import j_ee_project.j_ee_students_system.security.validation.ValidUsername;
import j_ee_project.j_ee_students_system.services.ValidationErrorResponse;
import j_ee_project.j_ee_students_system.services.resources.UserRoleResource;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.lang3.StringEscapeUtils;
import org.hibernate.validator.constraints.Email;

/**
 * REST Web Service
 *
 * @author Lightning
 */
@Path("admin/users")
@Stateless
public class UsersAdministrationService {

    @EJB
    UserDataManager userDataManager;
    @EJB
    StudentDataManager studentDataManager;
    @EJB
    LecturerDataManager lecturerDataManager;
    @EJB
    DegreeDataManager degreeDataManager;
    @EJB
    DisciplineDataManager disciplineDataManager;
    @EJB
    UserRoleDataManager userRoleDataManager;
    @EJB
    SpecialityDataManager specialityDataManager;
    @EJB
    SystemSecurityManager systemSecurityManager;
    @Inject
    UserSessionData userSessionData;

    @GET
    @Path("get")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get() {
//        System.out.println(UserRole.isValid("sdfsdf"));
//        UserRole ur = userRoleDataManager.find("lecturer");
//        userRoleDataManager.remove(ur);
        return Response.ok().build();
    }

    /**
     * Creates a new instance of UsersAdministrationService
     */
    public UsersAdministrationService() {
    }

    @POST
    @Path("create_user")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(
            @Valid @ValidUsername @FormParam("username") String username,
            @Valid @Size(min = User.MIN_PASSWORD_SIZE, max = User.MAX_PASSWORD_SIZE, message = "{j_ee_project.j_ee_students_system.security.validation.ValidPassword}") @FormParam("password") String password,
            @Valid @Size(min = User.MIN_PASSWORD_SIZE, max = User.MAX_PASSWORD_SIZE, message = "{j_ee_project.j_ee_students_system.security.validation.ValidPassword}") @FormParam("passwordRepeat") String passwordRepeat,
            @Valid @Email @FormParam("email") String email,
            @Valid @ValidPartOfName @FormParam("firstName") String firstName,
            @Valid @ValidPartOfName(message = "{j_ee_project.j_ee_students_system.security.validation.ValidSurname}") @FormParam("surname") String surname,
            @Valid @ValidPartOfName(message = "{j_ee_project.j_ee_students_system.security.validation.ValidLastName}") @FormParam("lastName") String lastName,
            @FormParam("usersRoles[]") List<String> usersRoles,
            @FormParam("isActivated") Integer isActivated
    ) {
        if (systemSecurityManager.isAuthorized(userSessionData, "create__users")) {
            username = StringEscapeUtils.escapeHtml4(username);
            email = StringEscapeUtils.escapeHtml4(email);            
            firstName = StringEscapeUtils.escapeHtml4(firstName);
            surname = StringEscapeUtils.escapeHtml4(surname);
            lastName = StringEscapeUtils.escapeHtml4(lastName);

            List<String> errors = new ArrayList();
            
            if(!password.equals(passwordRepeat)){
                errors.add("Въведените пароли не съвпадат!");
            }

            User enteredUser = userDataManager.getUser(username);
            if (enteredUser != null) {
                errors.add("Вече съществува потребител с въведеното потребителско име!");
            }

            User enteredUserEmail = userDataManager.getUser(username);
            if (enteredUserEmail != null) {
                errors.add("Вече съществува потребител с въведената електронна поща!");
            }

            Set<UserRole> validUserRoles = new HashSet<UserRole>(usersRoles.size());
            for (String roleName : usersRoles) {
                if (!UserRole.isValid(roleName)) {
                    errors.add("Невалидно име на роля.");
                    break;
                } else {
                    validUserRoles.add(userRoleDataManager.find(roleName));
                }
            }

            if (isActivated != 0 && isActivated != 1) {
                errors.add("Невалиден статут на активация.");
            }

            if (errors.isEmpty()) {
                User user;
                try {
                    user = new User(
                            username, systemSecurityManager.calculatePasswordHash(password), email, validUserRoles,
                            new PersonName(firstName, surname, lastName), isActivated == 1);
                    userDataManager.create(user);
                    return Response.ok().build();
                } catch (NoSuchAlgorithmException ex) {
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
                }

            }

            return Response.status(Response.Status.BAD_REQUEST).entity(new ValidationErrorResponse(errors)).build();
        }

        return Response.status(Response.Status.FORBIDDEN).build();

    }

    @POST
    @Path("create_student")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createStudent(
            @Valid @ValidUsername @FormParam("username") String username,
            @Valid @Size(min = User.MIN_PASSWORD_SIZE, max = User.MAX_PASSWORD_SIZE, message = "{j_ee_project.j_ee_students_system.security.validation.ValidPassword}") @FormParam("password") String password,
            @Valid @Size(min = User.MIN_PASSWORD_SIZE, max = User.MAX_PASSWORD_SIZE, message = "{j_ee_project.j_ee_students_system.security.validation.ValidPassword}") @FormParam("passwordRepeat") String passwordRepeat,
            @Valid @Email @FormParam("email") String email,
            @Valid @ValidUsername(message = "{j_ee_project.j_ee_students_system.security.validation.ValidFacultyNumber}") @FormParam("facultyNumber") String facultyNumber,
            @Valid @ValidPartOfName(message = "{j_ee_project.j_ee_students_system.security.validation.ValidFirstName}") @FormParam("firstName") String firstName,
            @Valid @ValidPartOfName(message = "{j_ee_project.j_ee_students_system.security.validation.ValidSurname}") @FormParam("surname") String surname,
            @Valid @ValidPartOfName(message = "{j_ee_project.j_ee_students_system.security.validation.ValidLastName}") @FormParam("lastName") String lastName,
            @FormParam("usersRoles[]") List<String> usersRoles,
            @FormParam("specialities[]") List<String> specialities,
            @FormParam("isActivated") Integer isActivated
    ) {
        if (systemSecurityManager.isAuthorized(userSessionData, "create__students")) {
            username = StringEscapeUtils.escapeHtml4(username);
            email = StringEscapeUtils.escapeHtml4(email);            
            facultyNumber = StringEscapeUtils.escapeHtml4(facultyNumber);
            firstName = StringEscapeUtils.escapeHtml4(firstName);
            surname = StringEscapeUtils.escapeHtml4(surname);
            lastName = StringEscapeUtils.escapeHtml4(lastName);
            List<String> errors = new ArrayList();

            if(!password.equals(passwordRepeat)){
                errors.add("Въведените пароли не съвпадат!");
            }
            
            User enteredUser = userDataManager.getUser(username);
            if (enteredUser != null) {
                errors.add("Вече съществува потребител с въведеното потребителско име!");
            }

            User enteredUserEmail = userDataManager.getUser(username);
            if (enteredUserEmail != null) {
                errors.add("Вече съществува потребител с въведената електронна поща!");
            }

            Set<UserRole> validUserRoles = new HashSet<UserRole>(usersRoles.size());
            for (String roleName : usersRoles) {
                if (!UserRole.isValid(roleName)) {
                    errors.add("Невалидно име на роля.");
                    break;
                } else {
                    validUserRoles.add(userRoleDataManager.find(roleName));
                }
            }

            Set<Speciality> validSpecialities = new HashSet<Speciality>(specialities.size());
            for (String speciality : specialities) {
                try {
                    Long specialityId = Long.parseUnsignedLong(speciality);
                    validSpecialities.add(specialityDataManager.find(specialityId));
                } catch (Exception e) {
                    errors.add("Невалидно име на специалност.");
                    break;
                }
            }
            if (validSpecialities.size() == 0) {
                errors.add("Трябва да изберете поне една специалност.");
            }

            if (isActivated != 0 && isActivated != 1) {
                errors.add("Невалиден статут на активация.");
            }

            if (errors.isEmpty()) {
                Student user;
                try {
                    user = new Student(
                            facultyNumber, validSpecialities,
                            username, systemSecurityManager.calculatePasswordHash(password), email, validUserRoles,
                            new PersonName(firstName, surname, lastName), isActivated == 1);
                    studentDataManager.create(user);
                    for (Speciality speciality : validSpecialities) {
                        speciality.addStudent(user);
                        specialityDataManager.edit(speciality);
                    }
                    return Response.ok().build();
                } catch (NoSuchAlgorithmException ex) {
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
                }

            }

            return Response.status(Response.Status.BAD_REQUEST).entity(new ValidationErrorResponse(errors)).build();
        }

        return Response.status(Response.Status.FORBIDDEN).build();

    }
    
    @POST
    @Path("create_lecturer")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createLecturer(
            @Valid @ValidUsername @FormParam("username") String username,
            @Valid @Size(min = User.MIN_PASSWORD_SIZE, max = User.MAX_PASSWORD_SIZE, message = "{j_ee_project.j_ee_students_system.security.validation.ValidPassword}") @FormParam("password") String password,
            @Valid @Size(min = User.MIN_PASSWORD_SIZE, max = User.MAX_PASSWORD_SIZE, message = "{j_ee_project.j_ee_students_system.security.validation.ValidPassword}") @FormParam("passwordRepeat") String passwordRepeat,
            @Valid @Email @FormParam("email") String email,
            @FormParam("information") String information,
            @Valid @ValidPartOfName(message = "{j_ee_project.j_ee_students_system.security.validation.ValidFirstName}") @FormParam("firstName") String firstName,
            @Valid @ValidPartOfName(message = "{j_ee_project.j_ee_students_system.security.validation.ValidSurname}") @FormParam("surname") String surname,
            @Valid @ValidPartOfName(message = "{j_ee_project.j_ee_students_system.security.validation.ValidLastName}") @FormParam("lastName") String lastName,
            @FormParam("usersRoles[]") List<String> usersRoles,
            @FormParam("disciplines[]") List<String> disciplines,
            @FormParam("degrees[]") List<String> degrees,
            @FormParam("isActivated") Integer isActivated
    ) {
        if (systemSecurityManager.isAuthorized(userSessionData, "create__lecturers")) {
            username = StringEscapeUtils.escapeHtml4(username);
            email = StringEscapeUtils.escapeHtml4(email);           
            information = StringEscapeUtils.escapeHtml4(information);
            firstName = StringEscapeUtils.escapeHtml4(firstName);
            surname = StringEscapeUtils.escapeHtml4(surname);
            lastName = StringEscapeUtils.escapeHtml4(lastName);
            List<String> errors = new ArrayList();

              if(!password.equals(passwordRepeat)){
                errors.add("Въведените пароли не съвпадат!");
            }
            User enteredUser = userDataManager.getUser(username);
            if (enteredUser != null) {
                errors.add("Вече съществува потребител с въведеното потребителско име!");
            }

            User enteredUserEmail = userDataManager.getUser(username);
            if (enteredUserEmail != null) {
                errors.add("Вече съществува потребител с въведената електронна поща!");
            }

            Set<UserRole> validUserRoles = new HashSet<UserRole>(usersRoles.size());
            for (String roleName : usersRoles) {
                if (!UserRole.isValid(roleName)) {
                    errors.add("Невалидно име на роля.");
                    break;
                } else {
                    validUserRoles.add(userRoleDataManager.find(roleName));
                }
            }

            Set<Discipline> validDisciplines= new HashSet<Discipline>(disciplines.size());
            for (String discipline : disciplines) {
                try {
                    Long disciplineId = Long.parseUnsignedLong(discipline);
                    validDisciplines.add(disciplineDataManager.find(disciplineId));
                } catch (Exception e) {
                    errors.add("Невалидно име на дисциплина.");
                    break;
                }
            }
            
            Set<Degree> validDegrees = new HashSet<Degree>(degrees.size());
            for (String degree : degrees){
                try {
                    Long degreeId = Long.parseUnsignedLong(degree);
                    validDegrees.add(degreeDataManager.find(degreeId));
                } catch (Exception e) {
                    errors.add("Невалидно име на академична степен (титла).");
                    break;
                }
            }
            if (validDisciplines.size() == 0) {
                errors.add("Трябва да изберете поне една дисциплина.");
            }

            if (isActivated != 0 && isActivated != 1) {
                errors.add("Невалиден статут на активация.");
            }

            if (errors.isEmpty()) {
                Lecturer user;
                try {
                    user = new Lecturer(validDegrees, validDisciplines, username, 
                            systemSecurityManager.calculatePasswordHash(password),
                            email, validUserRoles, new PersonName(firstName, surname, lastName), true);
                    lecturerDataManager.create(user);
                    for (Discipline discipline : validDisciplines) {
                        discipline.addLecturer(user);
                        disciplineDataManager.edit(discipline);
                    }
                    
                    for (Degree degree : validDegrees){
                        degree.addLecturer(user);
                        degreeDataManager.edit(degree);
                    }
                    return Response.ok().build();
                } catch (NoSuchAlgorithmException ex) {
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
                }

            }

            return Response.status(Response.Status.BAD_REQUEST).entity(new ValidationErrorResponse(errors)).build();
        }

        return Response.status(Response.Status.FORBIDDEN).build();

    }
    
    
    @POST
    @Path("create_degree")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createDegree(@Valid @Size(min = Degree.MIN_DEGREE_NAME_SIZE, max = Degree.MAX_DEGREE_NAME_SIZE, message = "{j_ee_project.j_ee_students_system.security.validation.ValidDegreeName}") @FormParam("degreeName") String degreeName){
        if (!systemSecurityManager.isAuthorized(userSessionData, "create__users")) {
             return Response.status(Response.Status.FORBIDDEN).build();
        }
        degreeName = StringEscapeUtils.escapeHtml4(degreeName);
        Degree degree = new Degree(degreeName);
        degreeDataManager.create(degree);
        return Response.ok().build();
    }    
   

    @GET
    @Path("get_users_roles")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsersRoles() {
        if (systemSecurityManager.isAuthorized(userSessionData, "read__users")) {
            List<UserRole> usersRoles = userRoleDataManager.findAll();
            UserRoleResource[] usersRolesStrings = new UserRoleResource[usersRoles.size()];
            int i = 0;
            for (UserRole userRole : usersRoles) {
                usersRolesStrings[i++] = new UserRoleResource(userRole.getRoleName(), userRole.getRoleTitle());
            }

            return Response.ok().entity(usersRolesStrings).build();
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }
}
