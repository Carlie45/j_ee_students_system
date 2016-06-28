package j_ee_project.j_ee_students_system.data_management;

import j_ee_project.j_ee_students_system.entities.UserRole;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Lightning
 */
@Stateless
public class UserRoleDataManager extends BasicDataManager<UserRole> {

    @PersistenceContext
    private EntityManager entityManager;

    public UserRoleDataManager() {
        super(UserRole.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }   

}
