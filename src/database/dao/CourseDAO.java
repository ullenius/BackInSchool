/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.dao;

import database.Course;
import database.Student;
import java.util.List;

/**
 *
 * @author Anosh D. Ullenius <anosh@anosh.se>
 */
public interface CourseDAO {
    
    public void addCourse(Course newCourse);
    public void deleteCourse(int id);
    public void updateCourseName(String newName, int id);
    public void updateSupervisor(int courseID, Integer supervisorID);
    public Course findCourse(int id);
    
    public List<Course> listAllCourses();
    public List<Student> listStudentsInCourse(int courseID);
    public List<Course> listUnsupervisedCourses();
}
