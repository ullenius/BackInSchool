package database.dao;

import database.Course;
import database.Student;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Anosh D. Ullenius <anosh@anosh.se>
 */
public interface CourseDAO {
    
    public void addCourse(Course newCourse);
    public void deleteCourse(int id);
    public void updateCourseName(String newName, int id);
    public void updateSupervisor(int courseID, Integer supervisorID) 
            throws CourseNotFoundException, TeacherNotFoundException;
    
    public Optional<Course> findCourse(int id);
    
    public List<Course> listAllCourses();
    public List<Student> listStudentsInCourse(int courseID);
    public List<Course> listUnsupervisedCourses();
}
