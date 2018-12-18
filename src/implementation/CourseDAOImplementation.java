/*
* Implementation of CourseDAO interface
*/
package implementation;

import database.dao.CourseNotFoundException;
import database.Course;
import database.Student;
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
    
    @Override
    public void deleteCourse(final int id) {
        em.getTransaction().begin();
        
        // Cleans up the @ManyToMany relationship with Courses in Education
        Query cleanUpEducationCourseLinkedTable = 
                em.createNativeQuery("DELETE FROM EDUCATION_COURSE WHERE "
                        + "courseGroup_ID = ?target;");
        cleanUpEducationCourseLinkedTable.setParameter("target", id);
        cleanUpEducationCourseLinkedTable.executeUpdate();
        
        Query myQuery = 
                em.createNativeQuery("DELETE FROM COURSE WHERE ID = ?target;");
        myQuery.setParameter("target", id);
        myQuery.executeUpdate();
        
        em.getTransaction().commit();
    }
    
    @Override
    public void updateCourseName(String newName, int id) {
         
        String sql = "UPDATE COURSE SET NAME=\"";
        sql = sql.concat(newName+"\" WHERE ID = " + id);
        
        customQuery(sql);
    }
    
     /**
     * Uses Integer so that it can be set to NULL since supervisor_ID
     * allows NULL in the database table. By sending null it gets parsed
     * by Java as null in the String used in the SQL-statement
     *
     * More precisely: UPDATE COURSE SET SUPERVISOR_ID = null
     * which is valid SQL :)
     *
     * @param courseID
     * @param supervisorID
     */
    @Override
    public void updateSupervisor(final int courseID, final Integer supervisorID) 
            throws CourseNotFoundException, TeacherNotFoundException {
        
        /** 
         * First lets check if the Course ID exists
         */
        
        final String courseExists = "SELECT ID FROM COURSE WHERE ID = " + courseID;
        if (!customQuery(courseExists))
            throw new CourseNotFoundException("The course does not exist! Can't set supervisor");
        
        /**
         * Everything went well. Now lets make sure that the supervisor ID 
         * (Teacher) exists as well
         */
        if (supervisorID != null) {
            
            final String teacherExists = "SELECT ID FROM TEACHER WHERE ID = "
                    + supervisorID;
            if (!customQuery(teacherExists))
                throw new TeacherNotFoundException("The teacher does not exist!");
        }
        
        // Everything went well, lets finally do the query
        String sql = "UPDATE COURSE SET SUPERVISOR_ID=" + supervisorID;
        sql = sql.concat(" WHERE ID = " + courseID);
        
        customQuery(sql);
    }
    
    @Override
    public List<Course> listAllCourses() {
        final String sql = "SELECT id,name,supervisor_id "
                + "FROM COURSE ORDER BY name;";
        
        em.getTransaction().begin();
        
        Query myQuery = em.createNativeQuery(sql,Course.class);
        List<Course> results = myQuery.getResultList();
        
        em.getTransaction().commit();
        return (results);
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
    public List<Course> listUnsupervisedCourses() {
        
        final String sql = "SELECT ID,NAME FROM COURSE WHERE SUPERVISOR_ID IS NULL";
        em.getTransaction().begin();
        
        Query myQuery = em.createNativeQuery(sql,Course.class);
        List<Course> results = myQuery.getResultList();
        
        em.getTransaction().commit();
        return (results);
    }
    @Override
     public Optional<Course> findCourse(final int id) {
         return findEntity(Course.class,id);
    }
    
    
}
