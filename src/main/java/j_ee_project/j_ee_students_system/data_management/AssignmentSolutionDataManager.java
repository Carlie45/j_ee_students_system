package j_ee_project.j_ee_students_system.data_management;

import j_ee_project.j_ee_students_system.entities.Assignment;
import j_ee_project.j_ee_students_system.entities.AssignmentSolution;
import j_ee_project.j_ee_students_system.entities.Discipline;
import j_ee_project.j_ee_students_system.entities.User;
import j_ee_project.j_ee_students_system.security.UserSessionData;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Lightning
 */
@Stateless
public class AssignmentSolutionDataManager extends BasicDataManager<AssignmentSolution> {

    @PersistenceContext
    private EntityManager entityManager;

    public AssignmentSolutionDataManager() {
        super(AssignmentSolution.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
    
    public AssignmentSolution getAssignmentSolution(User user, Assignment assignment){
         TypedQuery<AssignmentSolution> tq = entityManager.createQuery(
                "SELECT ASSSOLU FROM AssignmentSolution ASSSOLU WHERE ASSSOLU.submittedBy = ?1 AND ASSSOLU.assignment = ?2",
                AssignmentSolution.class
        );
        tq.setParameter(1, user);
        tq.setParameter(2, assignment);
        try {
            return tq.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean isUserOwnerOfThisFile(User user, String requestedFileName) {
        TypedQuery<String> tq = entityManager.createQuery(
                "SELECT ASSSOLU.fileName FROM AssignmentSolution ASSSOLU WHERE ASSSOLU.submittedBy = ?1",
                String.class
        );
        tq.setParameter(1, user);
        try {
            return tq.getSingleResult().equals(requestedFileName);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isLecturerLeadOfThisDiscipline(User user, String requestedFileName) {
        TypedQuery<AssignmentSolution> tq = entityManager.createQuery(
                "SELECT ASSSOLU  FROM AssignmentSolution ASSSOLU WHERE ASSSOLU.fileName = ?1",
                AssignmentSolution.class
        );
        tq.setParameter(1, requestedFileName);
        try {
            AssignmentSolution assignmentSolution = tq.getSingleResult();
            Assignment assignment = assignmentSolution.getAssignment();
            if(assignment == null){
                return false;
            }
            Discipline discipline = assignment.getDiscipline();
            if(discipline == null){
                return false;
            }
            Set<User> lecturers = discipline.getLecturers();
            return lecturers.contains(user);
        } catch (Exception e) {
            return false;
        }
    }

}
