package j_ee_project.j_ee_students_system.data_management;

import j_ee_project.j_ee_students_system.entities.UserRight;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Lightning
 */
@Stateless
public class UserRightDataManager extends BasicDataManager<UserRight> {

    @PersistenceContext
    private EntityManager entityManager;

    public UserRightDataManager() {
        super(UserRight.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }   

}
