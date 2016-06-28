package j_ee_project.j_ee_students_system.data_management;

import j_ee_project.j_ee_students_system.entities.Assignment;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Lightning
 */
@Stateless
public class AssignmentDataManager extends BasicDataManager<Assignment>{
    
    @PersistenceContext
    private EntityManager entityManager;

    
    public AssignmentDataManager() {
        super(Assignment.class);
    }
    
    
    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }  
    
    
    
}
