package j_ee_project.j_ee_students_system.data_management;

import j_ee_project.j_ee_students_system.entities.Speciality;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Lightning
 */
@Stateless
public class SpecialityDataManager extends BasicDataManager<Speciality>{
    
    @PersistenceContext
    private EntityManager entityManager;

    
    public SpecialityDataManager() {
        super(Speciality.class);
    }

    
    
    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }    
    
    
}
