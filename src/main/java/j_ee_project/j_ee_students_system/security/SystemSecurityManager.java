package j_ee_project.j_ee_students_system.security;

import j_ee_project.j_ee_students_system.data_management.UserDataManager;
import j_ee_project.j_ee_students_system.data_management.UserRightDataManager;
import j_ee_project.j_ee_students_system.entities.User;
import j_ee_project.j_ee_students_system.entities.UserRight;
import j_ee_project.j_ee_students_system.entities.UserRole;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Lightning
 */
@Stateless
public class SystemSecurityManager {

    @EJB
    UserDataManager userDataManager;
    @EJB
    UserRightDataManager userRightDataManager;

    public String calculatePasswordHash(String clearTextPassword) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
        return Base64.getEncoder().encodeToString(messageDigest.digest(clearTextPassword.getBytes()));
    }

    /**
     * Извършва идентифициране и потвърждаване на самоличността на потребителите
     * в системата (authentication) 
     * @param username - потребителско име
     * @param password - парола
     * @param userSessionData - потребителска сесия
     * @return true при успех.
     */
    public boolean authenticate(String username, String password, UserSessionData userSessionData) {
        User user = userDataManager.getUser(username);
        if (user != null) {
            try {
                String enteredPasswordHash = calculatePasswordHash(password);
                if (user.getPassword().equals(enteredPasswordHash) && user.isActivated()) {
                    userSessionData.setUserId(user.getId());
                    return true;
                }
            } catch (NoSuchAlgorithmException ex) {
                return false;
            }

        }
        return false;
    }

    /**
     * По зададена потребителска сесия, проверява дали идентифицирания вече
     * потвърдената самоличност на потребител е актуална, като за целта се
     * прави справка с авторитета (authority) управляващ потребителите
     * @param userSessionData - потребителска сесия
     * @return true <=> потребителят е валиден и с разрешен достъп, спрямо авторитета (authority)
     */
    public boolean isAuthenticated(UserSessionData userSessionData) {
        Long userId = userSessionData.getUserId();
        return (userId != null && userDataManager.isUserActivated(userId));
    }
    
    /**
     * По зададена потребителска сесия, осъществява контрол на нивото на досъп на
     * идентифициралия се потребител до системата, като за целта се
     * прави справка с авторитета (authority) управляващ потребителите
     * @param userSessionData - потребителска сесия
     * @param rightName - изисквано право
     * @return true <=> идентифицирания потребител, притежава роля, притежаваща изискваното право
     */
    public boolean isAuthorized(UserSessionData userSessionData, String rightName) {
        boolean hasRight = false;
        User user = userDataManager.find(userSessionData.getUserId());
        Set<UserRole> userRoles = user.getRoles();

        UserRight userRight = userRightDataManager.find(rightName);
        if (userRight != null) {
            Set<UserRole> rolesAllowed = userRight.getRolesAllowed();
            for (UserRole userRole : userRoles) {
                if (rolesAllowed.contains(userRole)) {
                    hasRight = true;
                    break;
                }
            }
        }
      
        return hasRight;
    }

    /**
     * Унищожава потребителска сесия, с цел осъществяване на изход от системата
     * на вече идентифициран потребител
     * @param userSessionData - потребителска
     * @return true при успех
     */
    public boolean logout(UserSessionData userSessionData) {
        userSessionData.setUserId(null);
        return userSessionData.getUserId() == null;
    }

}
