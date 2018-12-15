/*
* This is a helper class for miscellaneous data listings
* from the database. Mostly using native SQL queries.
* Did not fit within the other categories
* (and bonus sounds more fun than misc)
*
* ALL methods are static
*
*/
package menu;

import implementation.DaoImplementation;
import util.InputHelper;

/**
 *
 * @author Anosh D. Ullenius <anosh@anosh.se>
 */
public final class BonusMenu {
    
    private static final String STUDENTS_COURSE;
    private static final String STUDENTS_EDUCATION;
    private static final String LONELY_TEACHERS;
    private static final String LONELY_STUDENTS;
    private static final String UNSUPERVISED_COURSES;
    private static final String EXIT;
    
    private static final DaoImplementation schoolDB;
    private static final InputHelper feedMe;
    
    static {
        STUDENTS_COURSE = "List all STUDENTS from a course";
        STUDENTS_EDUCATION = "List all STUDENTS from an education";
        UNSUPERVISED_COURSES = "List all courses *WITHOUT* a supervisor";
        LONELY_STUDENTS = "List STUDENTS not tied to an EDUCATION";
        LONELY_TEACHERS = "List TEACHERS who do not SUPERVISE any COURSES";
        EXIT = "Exit menu";
        feedMe = new InputHelper();
        schoolDB = DaoImplementation.getInstance();
    }
    
    private BonusMenu() { // exits only to prevent instantiation
    }
    
    public static void printMenu() {
        
        int userChoice = -1;
        
        System.out.println("BONUS SUBMENU");
        System.out.println("Bonus options");
        
        System.out.println("1. " + STUDENTS_COURSE);
        System.out.println("2. " + STUDENTS_EDUCATION);
        System.out.println("3. " + UNSUPERVISED_COURSES);
        System.out.println("4. " + LONELY_STUDENTS);
        System.out.println("5. " + LONELY_TEACHERS);
        System.out.println("6. " + EXIT);
        
        int targetID = 0;
        
        while (true) {
            userChoice = feedMe.getInt("Please make your selection:");
            switch (userChoice) {
                
                case 1:
                    System.out.println(STUDENTS_COURSE);
                    targetID = feedMe.getInt("Enter COURSE id: ");
                    schoolDB.listStudentsInCourse(targetID).forEach(System.out::println);
                    break;
                case 2:
                    System.out.println(STUDENTS_EDUCATION);
                    targetID = feedMe.getInt("Enter EDUCATION id: ");
                    schoolDB.findEducation(targetID).getGroupOfStudents().forEach(System.out::println);
                    break;
                case 3:
                    System.out.println(UNSUPERVISED_COURSES);
                    System.out.println("Displaying all UNSUPERVISED courses");
                    schoolDB.listUnsupervisedCourses().forEach(System.out::println);
                    break;
                case 4:
                    System.out.println(LONELY_STUDENTS);
                    schoolDB.listLonelyStudents().forEach(System.out::println);
                    break;
                case 5:
                    System.out.println(LONELY_TEACHERS);
                    schoolDB.listLonelyTeachers().forEach(System.out::println);
                    break;
                case 6:
                    System.out.println("Exiting menu");
                    return; // exiting method
                default:
                    System.out.println("Invalid selection");
                    break;
            }
        }
    }
    
}
