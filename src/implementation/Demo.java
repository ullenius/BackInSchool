package implementation;

import database.Course;
import database.Education;
import database.Person;
import database.Student;
import database.Teacher;
import java.util.Iterator;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


/**
 *
 * @author Anosh D. Ullenius <anosh@anosh.se>
 */
public class Demo {
    
    public static void main(String[] args) {
      
        
        DaoImplementation schoolDB = new DaoImplementation();
//        Student student1 = new Student("Kalle");
//        Student student2 = new Student("Pandora");
//        
//        Education fullstack = new Education("Fullstack");
//        Education testare = new Education("Testare");
//        fullstack.addStudent(student1);
//        fullstack.addStudent(student2);
//        
//        schoolDB.addPerson(student1);
//        schoolDB.addPerson(student2);
//        
//        
//        Course course1 = new Course("SQL");
//        Course course2 = new Course("Agile");
//        schoolDB.addCourse(course1);
//        schoolDB.addCourse(course2);
//        fullstack.addCourse(course1);
//        fullstack.addCourse(course2);
//        testare.addCourse(course2);
//        
//        schoolDB.addEducation(fullstack);
//        schoolDB.addEducation(testare);
//        Teacher teacher1 = new Teacher("Uffe");
//        course1.setSupervisor(teacher1);
//        
//       schoolDB.addTeacher(teacher1);
        
        schoolDB.deleteCourse(4);
 
       
        //schoolDB.deleteStudent(1);
       //schoolDB.deleteTeacher(4);
       //schoolDB.deleteCourse(3);
        
//        Person elev = schoolDB.findById(0, new Student());
//        System.out.println(elev);
    }
}
