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
        Student student1 = new Student("Kalle");
        Student student2 = new Student("Pandora");
        
        Education fullstack = new Education("Fullstack");
        fullstack.addStudent(student1);
        fullstack.addStudent(student2);
        
        schoolDB.addPerson(student1);
        schoolDB.addPerson(student2);
        
        schoolDB.addEducation(fullstack);
       
        //schoolDB.deleteStudent(1);
        
//        Person elev = schoolDB.findById(0, new Student());
//        System.out.println(elev);
    }
}
