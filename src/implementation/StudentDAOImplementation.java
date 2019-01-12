package implementation;

import database.Education;
import database.Person;
import database.Student;
import database.dao.StudentDAO;
import database.dao.StudentNotFoundException;
import java.util.List;
import java.util.Optional;
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
        
        em.getTransaction().begin();
       Query myQuery = em.createNativeQuery("UPDATE STUDENT SET NAME = ?newname WHERE ID = ?value");
       myQuery.setParameter("newname", newName);
       myQuery.setParameter("value", id);

       myQuery.executeUpdate();
       em.getTransaction().commit();
    }
    
    /**
     * 
     * Cleans up the @OneToMany relationship with Student in Education
     * using JPQL before deleting the Student
     * 
     * @param id
     * @throws StudentNotFoundException (and performs rollback)
     */
    @Override
    public void deleteStudent(final int id) throws StudentNotFoundException {
        
        em.getTransaction().begin();
        
        // First, before we do anything else, we check if the Student exists
        Student student = em.find(Student.class,id);
        if (student == null) {
            em.getTransaction().rollback();
            throw new StudentNotFoundException("Deletion failed.Student: " + id
                    + " not found in database");
        }
        Query myQuery = em.createQuery("SELECT e FROM Education e WHERE :value MEMBER OF e.studentGroup", Student.class);
        myQuery.setParameter("value", student);
        List<Education> educationsContainingStudent = myQuery.getResultList();
        
        for (Education education : educationsContainingStudent)
            education.deleteStudent(student);
        
        em.remove(student); // finally, remove the student object (entry) itself
        em.getTransaction().commit();
    }
    
    @Override
    public Optional<Student> findStudentById(final int id) {
        
        return findEntity(Student.class,id);
    }
    
    @Override
    public List<Student> listStudentsInCourse(final int courseID) {
        
        final String sql = "SELECT STUDENT.ID, STUDENT.NAME "
                + "FROM STUDENT,EDUCATION_COURSE,COURSE "
                + "WHERE COURSE.ID = EDUCATION_COURSE.courseGroup_ID "
                + "AND COURSE.ID = " + courseID
                + " GROUP BY STUDENT.NAME "
                + "ORDER BY STUDENT.NAME DESC;";
        return getResultList(Student.class,sql);
    }
    
    @Override
    public List<Student> listAllStudents() {
        
        final String sql = "SELECT id,name FROM STUDENT ORDER BY NAME;";
        return getResultList(Student.class,sql);
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
        return getResultList(Student.class,sql);
    }
    
}
