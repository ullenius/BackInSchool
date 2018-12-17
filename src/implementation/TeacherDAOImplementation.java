/*
* 
*/
package implementation;

import database.Person;
import database.Teacher;
import database.dao.TeacherDAO;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Anosh D. Ullenius <anosh@anosh.se>
 */
public class TeacherDAOImplementation implements TeacherDAO {
    
    private static TeacherDAOImplementation instance;
    private static final EntityManagerFactory emf;
    private static final EntityManager em;
    
    static {
        emf = Persistence.createEntityManagerFactory("BackInSchoolPU");
        instance = null; //singleton stuff. See getInstance()
        em = emf.createEntityManager();
    }
    
    private TeacherDAOImplementation() {
        // Exists only to defeat instantiation
    }
    
    public static TeacherDAOImplementation getInstance() {
        if (instance == null) {
            instance = new TeacherDAOImplementation();
        }
        return instance;
    }
    
    @Override
    public void deleteTeacher(int id) {
        em.getTransaction().begin();
        
        // BEHÖVER KOD SOM ÄNDRAR ALLA ENTRIES
        // I COURSE DÄR SUPER_VISOR ID = id (teacher id i paramtern)
        // till NULL
        
        // FOREIGN KEY, teacher.id = course.supervisor_id
        Query supervisorCleanup = em.createNativeQuery("UPDATE COURSE SET SUPERVISOR_ID = NULL WHERE SUPERVISOR_ID = ?target;");
        supervisorCleanup.setParameter("target", id);
        supervisorCleanup.executeUpdate();
        
        Query myQuery = em.createNativeQuery("DELETE FROM TEACHER WHERE ID = ?target;");
        myQuery.setParameter("target", id);
        
        myQuery.executeUpdate();
        
        em.getTransaction().commit();
    }
    
    @Override
    public void addPerson(Person personToAdd) {
        em.getTransaction().begin();
        
        em.persist(personToAdd);
        
        em.getTransaction().commit();
    }
    
    @Deprecated
    // Deprecated by addPerson method
    public void addTeacher(Teacher teacherToAdd) {

        em.getTransaction().begin();
        em.persist(teacherToAdd);
        em.getTransaction().commit();
    }
    
    @Override
    public Teacher findTeacher(int id) {
        em.getTransaction().begin();
        
        Teacher result = em.find(Teacher.class, id);
        em.getTransaction().commit();
        
        return result;
    }
    
    @Override
    public List<Teacher> listAllTeachers() {
        final String sql = "SELECT id,name FROM TEACHER ORDER BY name;";
        
        em.getTransaction().begin();
        
        Query myQuery = em.createNativeQuery(sql,Teacher.class);
        List<Teacher> results = myQuery.getResultList();
        
        em.getTransaction().commit();
        return (results);
    }
    
    @Override
    public List<Teacher> listLonelyTeachers() {
        
        /**
         * Lists all TEACHERS who do NOT supervise any COURSE
         * that is, *WITHOUT* ties to any COURSE
         */
        final String sql = "SELECT TEACHER.NAME,TEACHER.ID,COURSE.SUPERVISOR_ID " +
                "FROM TEACHER " +
                "LEFT OUTER JOIN COURSE " +
                "ON TEACHER.ID = COURSE.SUPERVISOR_ID " +
                "WHERE SUPERVISOR_ID IS NULL;";
        em.getTransaction().begin();
        
        Query myQuery = em.createNativeQuery(sql,Teacher.class);
        List<Teacher> results = myQuery.getResultList();
        
        em.getTransaction().commit();
        return (results);
    }
    
    @Override
    public void updateTeacherName(String newName, int id) {
        
        String sql = "UPDATE TEACHER SET NAME=\"";
        sql = sql.concat(newName+"\" WHERE ID = " + id);
        
        customQuery(sql);
    }
    
    private boolean customQuery(final String customQuery) {
        em.getTransaction().begin();
        
        Query myQuery = em.createNativeQuery(customQuery);
        int result = myQuery.executeUpdate();
        
        em.getTransaction().commit();
        
        return (result > 0);
    }
}
