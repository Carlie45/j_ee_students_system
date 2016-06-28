package j_ee_project.j_ee_students_system.data_management;

import j_ee_project.j_ee_students_system.entities.Degree;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Lightning
 */
@Stateless
public class DegreeDataManager extends BasicDataManager<Degree>{
    
    @PersistenceContext
    private EntityManager entityManager;

    
    public DegreeDataManager() {
        super(Degree.class);
    }

    
    
    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }    
    
    
}
