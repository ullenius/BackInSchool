/*
* Implementation of CourseDAO interface
*/
package implementation;

import database.dao.CourseNotFoundException;
import database.Course;
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
     * TODO: Fix the cleanup using JPQL-queries
     * 
     * @param id
     * @throws CourseNotFoundException 
     */
    
    @Override
    public void deleteCourse(final int id) throws CourseNotFoundException {
        em.getTransaction().begin();
        // Cleans up the @ManyToMany relationship with Courses in Education
        
        Query cleanUpEducationCourseLinkedTable =
                em.createNativeQuery("DELETE FROM EDUCATION_COURSE WHERE "
                        + "courseGroup_ID = " + id);
        cleanUpEducationCourseLinkedTable.executeUpdate();
        
        Course course = em.find(Course.class, id);
        if (course == null)
            throw new CourseNotFoundException("Course was not found");
        em.remove(course);
    }
    
    @Override
    public void updateCourseName(String newName, int id) {
        
        String sql = "UPDATE COURSE SET NAME=\"";
        sql = sql.concat(newName+"\" WHERE ID = " + id);
        customQuery(sql);
    }
    /**
     * 
     * Uses Integer so that it can be set to NULL since supervisor_ID
     * allows NULL in the database table. By sending null it gets parsed
     * by Java as null in the String used in the SQL-statement
     *
     * More precisely: UPDATE COURSE SET SUPERVISOR_ID = null
     * which is valid SQL :)
     * 
     * Throws CourseNotFoundException is course ID is invalid
     * Throws TeacherNotFoundException is the supervisor ID is invalid
     * 
     * @param courseID
     * @param supervisorID
     * @throws CourseNotFoundException
     * @throws TeacherNotFoundException 
     */
    @Override
    public void updateSupervisor(final int courseID, final Integer supervisorID)
            throws CourseNotFoundException, TeacherNotFoundException {
        /**
         * First lets check if the Course ID exists.
         * 
         * Calls a generic method getResultList that is defined
         * in the superclass. If it returns an empty list, then
         * the Course does not exist. We do it this way to avoid
         * dealing with NULL-values.
         * 
         * If the list is empty we throw CourseNotFoundException
         */
        final String courseExists = "SELECT ID,NAME,SUPERVISOR_ID "
                + "FROM COURSE WHERE ID = " + courseID;
        if (getResultList(Course.class,courseExists).isEmpty())
            throw new CourseNotFoundException("The course does not exist! "
                    + "Can't set supervisor");
        /**
         * Everything went well. Now lets make sure that the supervisor ID
         * (Teacher) exists as well. Same method as above. Otherwise throws
         * an StudentNotFoundException
         */
        if (supervisorID != null) {
            final String teacherExists = "SELECT ID FROM TEACHER WHERE ID = "
                    + supervisorID;
            if (getResultList(Teacher.class,teacherExists).isEmpty())
                throw new TeacherNotFoundException("The teacher does not exist!");
        }
        // Everything went well, lets finally do the query
        String sql = "UPDATE COURSE SET SUPERVISOR_ID=" + supervisorID;
        sql = sql.concat(" WHERE ID = " + courseID);
        customQuery(sql); // executes the query
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
