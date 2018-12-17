package implementation;

import database.Course;
import database.Education;
import database.Person;
import database.Student;
import database.Teacher;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import menu.AddMenu;
import menu.BonusMenu;
import menu.ListAllMenu;
import menu.MainMenu;
import util.InputHelper;


/**
 *
 * This is whole class is solely meant for TESTING STUFF
 * 
 * 
 * @author Anosh D. Ullenius <anosh@anosh.se>
 */
@Deprecated
public final class Demo {
    
    private Demo() {
    }
    
    public static void main(String[] args) {
        
        MainMenu.printMenu();
        
//        List<Integer> numbers = Arrays.asList(10,15,20,30,40);
//        Set<Integer> studentID = new HashSet<>();
//        // add them all to the HashSet
//        numbers.forEach( (e) -> studentID.add(e));
//                
//        removeStudentsFromEducation(15, studentID);
        
        System.out.println("exiting main-method!");
        System.exit(0);
        
        
        
      //  DaoImplementation schoolDB = DaoImplementation.getInstance();
        
     //   schoolDB.listStudentsInCourse(4);
        
        
//        Course myCourse = schoolDB.findCourse(4);
//        System.out.println("Supervisor for course " + myCourse.getName() + " = " + myCourse.getSupervisorName());
//        
//        
//        Education found = schoolDB.findEducation(6);
//        System.out.println("Utbildningen " + found.getName() + " innehåller kurserna:");
//        found.getCourseGroup().forEach(System.out::println);
//        
//        System.out.println("Utbildningen innehåller dessa studenter: ");
//        found.getGroupOfStudents().forEach(System.out::println);
//        
//        System.exit(0);
//        
//        
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
//
//        Teacher teacher1 = new Teacher("Uffe");
//        course1.setSupervisor(teacher1);
//        fullstack.addCourse(course1);
//        fullstack.addCourse(course2);
//        testare.addCourse(course2);
//
//        // adding the stuff to the database
//        schoolDB.addTeacher(teacher1);
//        schoolDB.addCourse(course1);
//        schoolDB.addCourse(course2);
//        schoolDB.addEducation(fullstack);
//        schoolDB.addEducation(testare);
     
        
       // schoolDB.deleteCourse(4);
        
       // Course search = schoolDB.findCourse();
 
       
        //schoolDB.deleteStudent(1);
       //schoolDB.deleteTeacher(4);
       //schoolDB.deleteCourse(3);
        
//        Person elev = schoolDB.findById(0, new Student());
//        System.out.println(elev);
    }
    
    // Testing stuff
    private static void updateStudentName(final String newName, final int id) {
        
        String sql = "UPDATE STUDENT SET NAME=\"";
        sql = sql.concat(newName+"\" WHERE ID = " + id);
        
        System.out.println(sql);
    }
    

     private static void addStudentsToEducation() {
        
         InputHelper feedMe = new InputHelper();
         
        int courseID = feedMe.getInt("Please enter EDUCATION id:");
        int input = 0;
        System.out.println("Please enter student ID to add to EDUCATION");

        Set<Integer> studentsToAdd = new HashSet<>();
        while (input != -1) {
            input = feedMe.getInt("One entry per line, enter -1 to end");
            if (input != -1)
                studentsToAdd.add(input);
        }
        
        final int EDUCATION_ID = 6;
        
        StringBuffer sql = new StringBuffer(" INSERT INTO EDUCATION_STUDENT"
                + " (Education_ID, studentGroup_ID) VALUES");

        Iterator myIterator = studentsToAdd.iterator();
        while (myIterator.hasNext()) {
            sql.append("("+ EDUCATION_ID + ","+myIterator.next()+"),");
        }
         // removes the last ',' character
        sql.deleteCharAt(sql.length()-1);
        
        System.out.println(sql);
     }
     
       public static void removeStudentsFromEducation(int educationID, final Set<Integer> studentIdsToRemove) {
        
         StringBuffer sql = new StringBuffer("DELETE FROM EDUCATION_STUDENT " +
        "WHERE Education_ID =" +educationID + " AND studentGroup_ID IN (");
        
        Iterator myIterator = studentIdsToRemove.iterator();
        while (myIterator.hasNext()) {
            sql.append(myIterator.next() + ",");
        }

        // replaces the last character ',' with an ending ')'
        sql.setCharAt(sql.length()-1, ')');
        System.out.println(sql);
        
        //customQuery(sql.toString());
    }
        
    
}
