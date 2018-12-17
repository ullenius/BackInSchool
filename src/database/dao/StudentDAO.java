/*
*
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
    public void addPerson(Person personToAdd);
    public void updateStudentName(String newName, int id);
    public void deleteStudent(int id);

    public Student findStudentById(int id);

    public List<Student> listStudentsInCourse(int courseID);
    public List<Student> listAllStudents();
    public List<Student> listLonelyStudents();
    
}
