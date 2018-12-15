/*
* This is a helper class containing the UPDATE menu
* and its implementation
*
* ALL methods are static
*
*/
package menu;

import database.Course;
import database.Education;
import database.Person;
import database.Student;
import implementation.DaoImplementation;
import util.InputHelper;

/**
 *
 * @author Anosh D. Ullenius <anosh@anosh.se>
 */
public final class UpdateMenu {
    
    private UpdateMenu() { // exists only to prevent instantiation
    }
    
    private static final String STUDENT;
    private static final String TEACHER;
    private static final String COURSE;
    private static final String SUPERVISOR;
    private static final String EDUCATION;
    private static final String EDUCATION_ADD_STUDENTS;
    private static final String EDUCATION_REMOVE_STUDENTS;

    private static final String EDUCATION_ADD_COURSES;
    private static final String EDUCATION_REMOVE_COURSES;
    private static final String EXIT;
    
    private static final DaoImplementation schoolDB;
    private static final InputHelper feedMe;
    
    static {
        STUDENT = "Update student";
        TEACHER = "Update teacher";
        COURSE = "Update course";
        
        SUPERVISOR = "Set a new supervisor";
        EDUCATION = "Update education";
        
        EDUCATION_ADD_STUDENTS = "ADD students to education";
        EDUCATION_REMOVE_STUDENTS = "DELETE students from education";
        EDUCATION_ADD_COURSES = "ADD courses to education";
        EDUCATION_REMOVE_COURSES = "DELETE courses from education";
        
        EXIT = "Exit menu";
        feedMe = new InputHelper();
        schoolDB = DaoImplementation.getInstance();
    }
    
    public static void printMenu() {
        
        int userChoice = -1;
        
        System.out.println("UPDATE submenu");
        System.out.println("UPDATING entries in database");
        
        System.out.println("1. " + STUDENT);
        System.out.println("2. " + TEACHER);
        System.out.println("3. " + COURSE);
        System.out.println("4. " + SUPERVISOR);
        System.out.println("5. " + EDUCATION);
        System.out.println("6. " + EDUCATION_ADD_STUDENTS);
        System.out.println("7. " + EDUCATION_REMOVE_STUDENTS);
        System.out.println("8. " + EDUCATION_ADD_COURSES);
        System.out.println("9. " + EDUCATION_REMOVE_COURSES);
        System.out.println("10. " + EXIT);
        
        while (true) {
            userChoice = feedMe.getInt("Please make your selection:");
            switch (userChoice) {
                
                case 1:
                    System.out.println(STUDENT);
                    break;
                case 2:
                    System.out.println(TEACHER);
                    break;
                case 3:
                    System.out.println(COURSE);
                    break;
                case 4:
                    System.out.println(SUPERVISOR);
                    break;
                case 5:
                    System.out.println(EDUCATION);
                    break;
                case 6:
                    System.out.println(EDUCATION_ADD_STUDENTS);
                    break;
                case 7:
                    System.out.println(EDUCATION_REMOVE_STUDENTS);
                    break;
                case 8:
                    System.out.println(EDUCATION_ADD_COURSES);
                    break;
                case 9:
                    System.out.println(EDUCATION_REMOVE_COURSES);
                    break;
                case 10:
                    System.out.println("Exiting menu");
                    return; // exiting method
                default:
                    System.out.println("Invalid selection");
                    break;
            }
        }
        
        
    }
    
    private static void updateStudent() {
        
        int targetID = feedMe.getInt("Please enter student id");
        
        Student updatedStudent = schoolDB.findStudentById(targetID);
        //Student src = updatedStudent;
        
    }
    
}
