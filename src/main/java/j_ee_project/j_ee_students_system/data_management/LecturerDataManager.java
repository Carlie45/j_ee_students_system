package j_ee_project.j_ee_students_system.data_management;

import j_ee_project.j_ee_students_system.entities.Lecturer;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Lightning
 */
@Stateless
public class LecturerDataManager extends BasicDataManager<Lecturer> {

    @PersistenceContext
    private EntityManager entityManager;

    public LecturerDataManager() {
        super(Lecturer.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

}
