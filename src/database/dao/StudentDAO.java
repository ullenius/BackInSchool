/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.dao;

import database.Person;
import database.Student;
import java.util.List;

/**
 *
 * @author Anosh D. Ullenius <anosh@anosh.se>
 */
public interface StudentDAO {
    
     @Deprecated
    public void addStudent(Student studentToAdd); // use addPerson instead
    public void addPerson(final Person personToAdd);
    public void updateStudentName(final String newName, final int id);
    public void deleteStudent(final int id);

    public Student findStudentById(final int id);

    public List<Student> listStudentsInCourse(final int courseID);
    public List<Student> listAllStudents();
    public List<Student> listLonelyStudents();
    
   
}
