/*
*
*/
package implementation;

import database.Person;
import database.Student;
import database.dao.StudentDAO;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Anosh D. Ullenius <anosh@anosh.se>
 */
public class StudentDAOImplementation implements StudentDAO {
    
    private static StudentDAOImplementation instance;
    private static final EntityManagerFactory emf;
    private static final EntityManager em;
    
    static {
        emf = Persistence.createEntityManagerFactory("BackInSchoolPU");
        instance = null; //singleton stuff. See getInstance()
        em = emf.createEntityManager();
    }
    
    private StudentDAOImplementation() {
        // Exists only to defeat instantiation
    }
    
    public static StudentDAOImplementation getInstance() {
        if (instance == null) {
            instance = new StudentDAOImplementation();
        }
        return instance;
    }
    
    @Override
    public void addPerson(final Person personToAdd) {
        em.getTransaction().begin();
        
        em.persist(personToAdd);
        
        em.getTransaction().commit();
    }
    
    @Override
    public void updateStudentName(final String newName, final int id) {
        String sql = "UPDATE STUDENT SET NAME=\"";
        sql = sql.concat(newName+"\" WHERE ID = " + id);
        
        customQuery(sql);
    }
    
    @Override
    public void deleteStudent(final int id) {
        
        em.getTransaction().begin();
        
        // deletes the students from the Educations that they are tied to
        Query cleanUp = em.createNativeQuery("DELETE FROM EDUCATION_STUDENT WHERE studentGroup_ID = ?target;");
        cleanUp.setParameter("target", id);
        cleanUp.executeUpdate();
        
        Query myQuery = em.createNativeQuery("DELETE FROM STUDENT WHERE ID = ?target;");
        myQuery.setParameter("target", id);
        
        myQuery.executeUpdate();
        
        em.getTransaction().commit();
    }
    
    /**
     *
     * Generic method that returns a Person object from the database
     * based on id-number (as stored in the database)
     *
     * @param <T>
     * @param id
     * @param person
     * @return
     */
    @Override
    public <T extends Person> Person findById(final int id, final T person) {
        
        em.getTransaction().begin();
        Person result = em.find(person.getClass(), id);
        
        return result;
    }
    
    @Override
    public Student findStudentById(final int id) {
        em.getTransaction().begin();
        Student foundStudent = em.find(Student.class, id);
        return foundStudent;
    }
    
    @Override
    public List<Student> listStudentsInCourse(final int courseID) {
        
        final String sql = "SELECT STUDENT.ID, STUDENT.NAME "
                + "FROM STUDENT,EDUCATION_COURSE,COURSE "
                + "WHERE COURSE.ID = EDUCATION_COURSE.courseGroup_ID "
                + "AND COURSE.ID = ?target "
                + "GROUP BY STUDENT.NAME "
                + "ORDER BY STUDENT.NAME DESC;";
        
        em.getTransaction().begin();
        
        Query myQuery = em.createNativeQuery(sql,Student.class);
        myQuery.setParameter("target", courseID);
        
        List<Student> results = myQuery.getResultList();
        
        em.getTransaction().commit();
        return (results);
    }
    
    @Override
    public List<Student> listAllStudents() {
        
        final String sql = "SELECT id,name FROM STUDENT ORDER BY NAME;";
        
        em.getTransaction().begin();
        
        Query myQuery = em.createNativeQuery(sql,Student.class);
        List<Student> results = myQuery.getResultList();
        
        em.getTransaction().commit();
        return (results);
    }
    
    @Override
    @Deprecated
    // DEPRECATED by addPerson()
    public void addStudent(final Student studentToAdd) {
        
        em.getTransaction().begin();
        
        em.persist(studentToAdd);
        
        em.getTransaction().commit();
    }
    
    /**
     * Performs custom native SQL-query
     * Helper method used by other methods in this class
     *
     * Dangerous! Use with caution
     * 
     * executeUpdate returns number of rows affected
     * if they are > 0 it was successfully executed
     *
     * @param customQuery
     */
    private boolean customQuery(final String customQuery) {
        em.getTransaction().begin();
        
        Query myQuery = em.createNativeQuery(customQuery);
        int result = myQuery.executeUpdate();
        
        em.getTransaction().commit();
        
        return (result > 0);
    }
    
}
