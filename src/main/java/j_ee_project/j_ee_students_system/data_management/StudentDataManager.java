package j_ee_project.j_ee_students_system.data_management;

import j_ee_project.j_ee_students_system.entities.Student;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Lightning
 */
@Stateless
public class StudentDataManager extends BasicDataManager<Student> {

    @PersistenceContext
    private EntityManager entityManager;

    public StudentDataManager() {
        super(Student.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

}
