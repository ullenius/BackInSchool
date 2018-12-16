/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.dao;

import database.Course;
import database.Student;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Anosh D. Ullenius <anosh@anosh.se>
 */
public interface CourseDAO {
    
    public void addCourse(final Course newCourse);
    public void deleteCourse(final int id);
    public void updateCourseName(final String newName, final int id);
    public void updateSupervisor(final int courseID, final Integer supervisorID);
    
    
    public List<Course> listAllCourses();
    public List<Student> listStudentsInCourse(final int courseID);
    public List<Course> listUnsupervisedCourses();
}
