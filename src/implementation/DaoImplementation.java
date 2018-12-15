/*

*/
package implementation;

import database.Course;
import database.Education;
import database.Person;
import database.Student;
import database.Teacher;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Anosh D. Ullenius <anosh@anosh.se>
 */
public class DaoImplementation {
    
    private static DaoImplementation instance;
    private static final EntityManagerFactory emf;
    private static EntityManager em;
    
    static {
        emf = Persistence.createEntityManagerFactory("BackInSchoolPU");
        instance = null; //singleton stuff. See getInstance()
    }
    
    private DaoImplementation() {
        // Exists only to defeat instantiation
        throw new AssertionError(); // if it somehow gets called anyway
    }
    
    public static DaoImplementation getInstance() {
        if (instance == null) {
            instance = new DaoImplementation();
        }
        return instance;
    }
    
    @Deprecated
    // Deprecated by addPerson method
    public void addTeacher(Teacher teacherToAdd) {

        em = emf.createEntityManager();
        em.getTransaction().begin();

        em.persist(teacherToAdd);

        em.getTransaction().commit();
    }
    
    public void deleteTeacher(final int id) {
        
        em = emf.createEntityManager();
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
    
    public void deleteStudent(final int id) {
        
        em = emf.createEntityManager();
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
    
    public void addPerson(final Person personToAdd) {
        
        em = emf.createEntityManager();
        em.getTransaction().begin();
        
        em.persist(personToAdd);
        
        em.getTransaction().commit();
    }
    
    
    public void addEducation(final Education newEducation) {
        
        em = emf.createEntityManager();
        em.getTransaction().begin();
        
        em.persist(newEducation);
        
        em.getTransaction().commit();
    }
    
    public void deleteEducation(final int id) {
        
        em = emf.createEntityManager();
        em.getTransaction().begin();
        
        Query myQuery = em.createNativeQuery("DELETE FROM EDUCATION WHERE ID = ?target;");
        myQuery.setParameter("target", id);
        
        myQuery.executeUpdate();
        
        em.getTransaction().commit();
    }
    
    public void addCourse(final Course newCourse) {
        
        em = emf.createEntityManager();
        em.getTransaction().begin();
        
        em.persist(newCourse);
        
        em.getTransaction().commit();
    }
    
    public void deleteCourse(final int id) {
        
        // kommer detta att fucka sig?
        
        em = emf.createEntityManager();
        em.getTransaction().begin();
        
        
        // Cleans up the @ManyToMany relationship with Courses in Education
        Query cleanUpEducationCourseLinkedTable = em.createNativeQuery("DELETE FROM EDUCATION_COURSE WHERE courseGroup_ID = ?target;");
        cleanUpEducationCourseLinkedTable.setParameter("target", id);
        cleanUpEducationCourseLinkedTable.executeUpdate();
        
        Query myQuery = em.createNativeQuery("DELETE FROM COURSE WHERE ID = ?target;");
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
    public <T extends Person> Person findById(final int id, T person) {
        
        em = emf.createEntityManager();
        em.getTransaction().begin();
        
        Person result = em.find(person.getClass(), id);
        
        return result;
    }
    
    public Student findStudentById(final int id) {
        em = emf.createEntityManager();
        em.getTransaction().begin();
        
        Student foundStudent = em.find(Student.class, id);
        
        return foundStudent;
    }
    
    @Deprecated
    // DEPRECATED by addPerson()
    public void addStudent(Student studentToAdd) {
        
        em = emf.createEntityManager();
        em.getTransaction().begin();
        
        em.persist(studentToAdd);
        
        em.getTransaction().commit();
    }
    
    public Course findCourse(final int id) {
        
        em = emf.createEntityManager();
        em.getTransaction().begin();
        
        Course result = em.find(Course.class, id);
        
        em.getTransaction().commit();
        
        return result;
    }
    
    public Education findEducation(final int id) {
        em = emf.createEntityManager();
        em.getTransaction().begin();
        
        Education result = em.find(Education.class, id);
        
        em.getTransaction().commit();
        
        return result;
    }
    
    public List<Student> listStudentsInCourse(final int courseID) {
        
        final String sql = "SELECT STUDENT.ID, STUDENT.NAME "
                + "FROM STUDENT,EDUCATION_COURSE,COURSE "
                + "WHERE COURSE.ID = EDUCATION_COURSE.courseGroup_ID "
                + "AND COURSE.ID = ?target "
                + "GROUP BY STUDENT.NAME "
                + "ORDER BY STUDENT.NAME DESC;";
        
        em = emf.createEntityManager();
        em.getTransaction().begin();
        
        Query myQuery = em.createNativeQuery(sql,Student.class);
        myQuery.setParameter("target", courseID);
        
        List<Student> results = myQuery.getResultList();
        
        em.getTransaction().commit();
        return (results);
    }
    
    public Teacher findTeacher(final int id) {
        em = emf.createEntityManager();
        em.getTransaction().begin();
        
        Teacher result = em.find(Teacher.class, id);
        em.getTransaction().commit();
        
        return result;
    }
    
    
    public List<Student> listAllStudents() {
        
        final String sql = "SELECT id,name FROM STUDENT ORDER BY NAME;";
        
        em = emf.createEntityManager();
        em.getTransaction().begin();
        
        Query myQuery = em.createNativeQuery(sql,Student.class);
        List<Student> results = myQuery.getResultList();
        
        em.getTransaction().commit();
        return (results);
    }
    
    public List<Teacher> listAllTeachers() {
        final String sql = "SELECT id,name FROM TEACHER ORDER BY name;";
        
        em = emf.createEntityManager();
        em.getTransaction().begin();
        
        Query myQuery = em.createNativeQuery(sql,Teacher.class);
        List<Teacher> results = myQuery.getResultList();
        
        em.getTransaction().commit();
        return (results);
    }
    
    public List<Course> listAllCourses() {
        final String sql = "SELECT id,name,supervisor_id "
                + "FROM COURSE ORDER BY name;";
        
        em = emf.createEntityManager();
        em.getTransaction().begin();
        
        Query myQuery = em.createNativeQuery(sql,Course.class);
        List<Course> results = myQuery.getResultList();
        
        em.getTransaction().commit();
        return (results);
    }
    
    public List<Education> listAllEducations() {
        final String sql = "SELECT id,name FROM EDUCATION ORDER BY name;";
        
        em = emf.createEntityManager();
        em.getTransaction().begin();
        
        Query myQuery = em.createNativeQuery(sql,Education.class);
        List<Education> results = myQuery.getResultList();
        
        em.getTransaction().commit();
        return (results);
    }
    
    public List<Course> listUnsupervisedCourses() {
        
        final String sql = "SELECT ID,NAME FROM COURSE WHERE SUPERVISOR_ID IS NULL";
        em = emf.createEntityManager();
        em.getTransaction().begin();
        
        Query myQuery = em.createNativeQuery(sql,Course.class);
        List<Course> results = myQuery.getResultList();
        
        em.getTransaction().commit();
        return (results);
    }
    
    public List<Student> listLonelyStudents() {
        /**
         * Lists all STUDENTS *WITHOUT* ties to any Education
         */
        final String sql = "SELECT STUDENT.ID,STUDENT.NAME " +
                "FROM STUDENT " +
                "LEFT OUTER JOIN EDUCATION_STUDENT " +
                "ON STUDENT.ID = EDUCATION_STUDENT.studentgroup_id " +
                "WHERE education_id IS NULL;";
        em = emf.createEntityManager();
        em.getTransaction().begin();
        
        Query myQuery = em.createNativeQuery(sql,Student.class);
        List<Student> results = myQuery.getResultList();
        
        em.getTransaction().commit();
        return (results);
    }
    
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
        em = emf.createEntityManager();
        em.getTransaction().begin();
        
        Query myQuery = em.createNativeQuery(sql,Teacher.class);
        List<Teacher> results = myQuery.getResultList();
        
        em.getTransaction().commit();
        return (results);
    }
    
}
    

