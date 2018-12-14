/*
* This is a helper class containing the DELETE menu
* and its implementation
*/
package menu;

import implementation.DaoImplementation;
import util.InputHelper;

/**
 *
 * @author Anosh D. Ullenius <anosh@anosh.se>
 */
public final class DeleteMenu {
    
    private static final String STUDENT;
    private static final String TEACHER;
    private static final String EDUCATION;
    private static final String COURSE;
    private static final String EXIT;
    
    private static final DaoImplementation schoolDB;
    private static final InputHelper feedMe;
    
    static {
        STUDENT = "Delete student";
        TEACHER = "Delete teacher";
        EDUCATION = "Delete education";
        COURSE = "Delete course";
        EXIT = "Exit menu";
        feedMe = new InputHelper();
        schoolDB = DaoImplementation.getInstance();
    }
    
    private DeleteMenu() { //private constructor
    }
    
    public void printMenu() {
        
        int userChoice = -1;
        
        System.out.println("DELETION SUBMENU");
        
        System.out.println("Deleting entries from database");
        
        System.out.println("1. " + STUDENT);
        System.out.println("2. " + TEACHER);
        System.out.println("3. " + COURSE);
        System.out.println("4. " + EDUCATION);
        System.out.println("5. " + EXIT);
        
        int targetID = -1;
        
        while (true) {
            userChoice = feedMe.getInt("Please make your selection:");
            switch (userChoice) {
                
                case 1:
                    targetID = feedMe.getInt("Enter STUDENT id to delete: ");
                    schoolDB.deleteStudent(targetID);
                    System.out.println("Deleting STUDENT" + targetID);
                    break;
                case 2:
                    targetID = feedMe.getInt("Enter TEACHER id to delete: ");
                    schoolDB.deleteTeacher(targetID);
                    System.out.println("Deleting TEACHER " + targetID);
                    break;
                case 3:
                    targetID = feedMe.getInt("Enter COURSE id to delete: ");
                    schoolDB.deleteCourse(targetID);
                    System.out.println("Deleting COURSE " + targetID);
                    break;
                case 4:
                    targetID = feedMe.getInt("Enter EDUCATION id to delete: ");
                    schoolDB.deleteEducation(targetID);
                    System.out.println("Deleting EDUCATION " + targetID);
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
