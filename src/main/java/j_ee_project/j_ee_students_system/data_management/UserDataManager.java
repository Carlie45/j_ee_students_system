package j_ee_project.j_ee_students_system.data_management;

import j_ee_project.j_ee_students_system.entities.User;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Lightning
 */
@Stateless
public class UserDataManager extends BasicDataManager<User> {

    @PersistenceContext
    private EntityManager entityManager;

    public UserDataManager() {
        super(User.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    public User getUser(String username) {
        TypedQuery<User> tq = entityManager.createQuery(
                "SELECT u FROM User u WHERE u.username = ?1",
                User.class
        );
        tq.setParameter(1, username);
        try {
            return tq.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean isUserActivated(Long id) {
        TypedQuery<Long> tq = entityManager.createQuery(
                "SELECT u.id FROM User u WHERE u.id = ?1 AND u.isActivated = TRUE",
                Long.class
        );
        tq.setParameter(1, id);        
        try {
            return tq.getSingleResult().equals(id);
        } catch (Exception e) {
            return false;
        }
    }

}
