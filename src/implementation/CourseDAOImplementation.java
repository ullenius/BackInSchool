/*
* Implementation of CourseDAO interface
*/
package implementation;

import database.dao.CourseNotFoundException;
import database.Course;
import database.Education;
import database.Student;
import database.Teacher;
import database.dao.CourseDAO;
import database.dao.TeacherNotFoundException;
import java.util.List;
import java.util.Optional;
import javax.persistence.Query;

/**
 *
 * @author Anosh D. Ullenius <anosh@anosh.se>
 */
public class CourseDAOImplementation extends AbstractImplementation implements CourseDAO {
    
    private static CourseDAOImplementation instance;
    
    static {
        instance = null; //singleton stuff. See getInstance()
    }
    
    private CourseDAOImplementation() {
        // Exists only to defeat instantiation
    }
    
    public static CourseDAOImplementation getInstance() {
        if (instance == null) {
            instance = new CourseDAOImplementation();
        }
        return instance;
    }
    
    @Override
    public void addCourse(final Course newCourse) {
        persistStuff(newCourse);
    }
    
    /**
     * 
     * Cleans up the @ManyToMany relationship with Courses in Education
     * using JPQL before deleting the Course.
     * 
     * @param id
     * @throws CourseNotFoundException (and performs rollback)
     */
    @Override
    public void deleteCourse(final int id) throws CourseNotFoundException {
        em.getTransaction().begin();
      
        // First, before we do anything. Check if the Course even exists
        Course course = em.find(Course.class, id);
        if (course == null) {
            em.getTransaction().rollback();
            throw new CourseNotFoundException("Course was not found");
        }
        
       Query myQuery = em.createQuery("SELECT e FROM Education e WHERE :value MEMBER OF e.courseGroup", Education.class);
       myQuery.setParameter("value", course);
       List<Education> educationsContainingCourse = myQuery.getResultList();
       
       for (Education education : educationsContainingCourse)
           education.deleteCourse(course);

        em.remove(course);
        em.getTransaction().commit();
    }
    
    /**
     * 
     * We don't need to persist. JPA performs dirty checking at the commit stage
     * and automatically updates the course if the object (name) has changed.
     * 
     * @param newName
     * @param id 
     * @throws CourseNotFoundException (and performs rollback)
     */
    @Override
    public void updateCourseName(String newName, int id) throws CourseNotFoundException {
        em.getTransaction().begin();
        Course course = em.find(Course.class,id);
        if (course == null) {
            em.getTransaction().rollback();
            throw new CourseNotFoundException("Course was not found");
        }
        course.setName(newName.trim()); // removes whitespace
        em.getTransaction().commit();
    }
    /**
     * 
     * Uses Integer so that it can be set to NULL since supervisor_ID
     * allows NULL in the database table. If the paramter is NULL then
     * we set the Teacher object to null and sets that object as supervisor for
     * the course ID.
     * 
     * Basic OOP and ORM code.
     * 
     * 
     * @param courseID
     * @param supervisorID
     * @throws CourseNotFoundException (and performs rollback)
     * @throws TeacherNotFoundException (and performs rollback)
     */
    @Override
    public void updateSupervisor(final int courseID, final Integer supervisorID)
            throws CourseNotFoundException, TeacherNotFoundException {
        
          em.getTransaction().begin();
          Course course = em.find(Course.class,courseID);
          if (course == null) {
              em.getTransaction().rollback();
              throw new CourseNotFoundException("Course was not found");
          }
         
          Teacher newSupervisor;
          if (supervisorID == null)
              newSupervisor = null;
          else {
              newSupervisor = em.find(Teacher.class,supervisorID);
              if (newSupervisor == null) {
                  em.getTransaction().rollback();
                  throw new TeacherNotFoundException("Teacher was not found");
              }
          }
        course.setSupervisor(newSupervisor);
        em.getTransaction().commit();
    }
    
    @Override
    public List<Course> listAllCourses() {
        final String sql = "SELECT id,name,supervisor_id "
                + "FROM COURSE ORDER BY name;";
        List<Course> results = getResultList(Course.class,sql);
        return (results);
    }
    
    /**
     * Performs simple SQL-join since Students are not tied directly
     * to a Course. 
     * 
     * Students are only tied to an Education. 
     * And Courses are only tied to an Education.
     * 
     * @param courseID
     * @return 
     */
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
    public List<Course> listUnsupervisedCourses() {
        
        final String sql = "SELECT ID,NAME FROM COURSE "
                + "WHERE SUPERVISOR_ID IS NULL";
        return getResultList(Course.class,sql);
    }
    @Override
    public Optional<Course> findCourse(final int id) {
        return findEntity(Course.class,id);
    }
    
}
