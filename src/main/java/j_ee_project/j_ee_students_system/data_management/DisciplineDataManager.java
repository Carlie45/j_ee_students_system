package j_ee_project.j_ee_students_system.data_management;

import j_ee_project.j_ee_students_system.entities.Discipline;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Lightning
 */
@Stateless
public class DisciplineDataManager extends BasicDataManager<Discipline>{
    
    @PersistenceContext
    private EntityManager entityManager;

    
    public DisciplineDataManager() {
        super(Discipline.class);
    }
    
    
    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }    
    
    
}
