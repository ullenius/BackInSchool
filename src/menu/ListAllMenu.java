/*
* This is a helper class for displaying all entries as Lists (java.util.List)
* obtained from their respective database tables. That is:
*
* Listing all Students
* Listing all Teachers
* Listing all Courses
* Listing all Educations
*/
package menu;

import implementation.DaoImplementation;
import util.InputHelper;

/**
 *
 * @author Anosh D. Ullenius <anosh@anosh.se>
 */
public final class ListAllMenu {
    
    private static final String STUDENT;
    private static final String TEACHER;
    private static final String EDUCATION;
    private static final String COURSE;
    private static final String EXIT;
    
    private static final DaoImplementation schoolDB;
    private static final InputHelper feedMe;
    
    static {
        STUDENT = "Display all students";
        TEACHER = "Display all teachers";
        EDUCATION = "Display all educations";
        COURSE = "Display all courses";
        EXIT = "Exit menu";
        feedMe = new InputHelper();
        schoolDB = new DaoImplementation();
    }
    
    private ListAllMenu() { //private constructor
    }
    
    public void printMenu() {
        
        int userChoice = -1;
        
        System.out.println("LIST ALL SUBMENU");
        
        System.out.println("Display all entries from database");
        
        System.out.println("1. " + STUDENT);
        System.out.println("2. " + TEACHER);
        System.out.println("3. " + COURSE);
        System.out.println("4. " + EDUCATION);
        System.out.println("5. " + EXIT);
        
        while (true) {
            userChoice = feedMe.getInt("Please make your selection:");
            switch (userChoice) {
                
                case 1:
                    System.out.println("Displaying all STUDENTS:");
                    schoolDB.listAllStudents().forEach(System.out::println);
                    break;
                case 2:
                    System.out.println("Displaying all TEACHERS:");
                    schoolDB.listAllTeachers().forEach(System.out::println);
                    break;
                case 3:
                    System.out.println("Displaying all COURSES:");
                    schoolDB.listAllCourses().forEach(System.out::println);
                    break;
                case 4:
                    System.out.println("Displaying all EDUCATIONS:");
                    schoolDB.listAllEducations().forEach(System.out::println);
                    break;
                case 5:
                    System.out.println("Exiting menu");
                    return; // exiting method
                default:
                    System.out.println("Invalid selection");
                    break;
            }
        }
        
    }
    
}
