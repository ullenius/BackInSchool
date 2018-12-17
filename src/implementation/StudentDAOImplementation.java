/*
*
*/
package implementation;

import database.Person;
import database.Student;
import database.dao.StudentDAO;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Anosh D. Ullenius <anosh@anosh.se>
 */
public class StudentDAOImplementation extends AbstractImplementation implements StudentDAO {
    
    private static StudentDAOImplementation instance;
    
    static {
        instance = null; //singleton stuff. See getInstance()
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
    @Deprecated
    // DEPRECATED by addPerson()
    public void addStudent(final Student studentToAdd) {
        persistStuff(studentToAdd);
    }
    
    @Override
    public void addPerson(final Person personToAdd) {
        persistStuff(personToAdd);
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

    @Override
    public Student findStudentById(final int id) {
        return findById(Student.class,id);
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
    /**
     * Lists all STUDENTS *WITHOUT* ties to any Education
     * 
     * The SQL-query is quite complicated due to the EclipseLink's implementation
     * of the JPA. It creates a linked table by default which is strictly not
     * necessary for the OneToMany-relationship between Education and Student
     * in the database.
     * 
     * The Students themselves don't have any knowledge of whether or not
     * they have a relationship to any other database table. It is a "naive"
     * entity in that sense. But since RDBMS are by nature uni-directional
     * we can simply perform a JOIN-operation to solve the problem at hand.
     * 
     */
    
    @Override
    public List<Student> listLonelyStudents() {
        
        final String sql = "SELECT STUDENT.ID,STUDENT.NAME " +
                "FROM STUDENT " +
                "LEFT OUTER JOIN EDUCATION_STUDENT " +
                "ON STUDENT.ID = EDUCATION_STUDENT.studentgroup_id " +
                "WHERE education_id IS NULL;";
        em.getTransaction().begin();
        
        Query myQuery = em.createNativeQuery(sql,Student.class);
        List<Student> results = myQuery.getResultList();
        
        em.getTransaction().commit();
        return (results);
    }
 
    
}
