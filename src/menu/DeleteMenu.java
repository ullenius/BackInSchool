/*
* This is a helper class containing the DELETE menu
* and its implementation
*
* ALL methods are static
*
*/
package menu;

import database.dao.StudentNotFoundException;
import database.dao.TeacherNotFoundException;
import static menu.AbstractMenu.courseDAO;

/**
 *
 * @author Anosh D. Ullenius <anosh@anosh.se>
 */
final class DeleteMenu extends AbstractMenu {
    
    private static final String STUDENT;
    private static final String TEACHER;
    private static final String EDUCATION;
    private static final String COURSE;
    
    static {
        STUDENT = "Delete student";
        TEACHER = "Delete teacher";
        EDUCATION = "Delete education";
        COURSE = "Delete course";
    }
    
    private DeleteMenu() { // exists only to prevent instantiation
    }
    
    public static void printMenu() {
        
        int userChoice = -1;
        int targetID = -1;
        
        while (true) {
            System.out.println("DELETION SUBMENU");
            System.out.println("Deleting entries from database");
            
            System.out.println("1. " + STUDENT);
            System.out.println("2. " + TEACHER);
            System.out.println("3. " + COURSE);
            System.out.println("4. " + EDUCATION);
            System.out.println("5. " + EXIT);
            
            userChoice = feedMe.getInt("Please make your selection:");
            switch (userChoice) {
                
                case 1:
                    System.out.println(STUDENT);
                    targetID = feedMe.getInt("Enter STUDENT id to delete: ");
                    System.out.println("Deleting STUDENT" + targetID);
                    try {
                        studentDAO.deleteStudent(targetID);
                    } catch (StudentNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    feedMe.getText("Press enter to continue");
                    break;
                case 2:
                    System.out.println(TEACHER);
                    targetID = feedMe.getInt("Enter TEACHER id to delete: ");
                    System.out.println("Deleting TEACHER " + targetID);
                    try {
                        teacherDAO.deleteTeacher(targetID);
                    } catch (TeacherNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    feedMe.getText("Press enter to continue");
                    break;
                case 3:
                    System.out.println(COURSE);
                    targetID = feedMe.getInt("Enter COURSE id to delete: ");
                    courseDAO.deleteCourse(targetID);
                    System.out.println("Deleting COURSE " + targetID);
                    feedMe.getText("Press enter to continue");
                    break;
                case 4:
                    System.out.println(EDUCATION);
                    targetID = feedMe.getInt("Enter EDUCATION id to delete: ");
                    educationDAO.deleteEducation(targetID);
                    System.out.println("Deleting EDUCATION " + targetID);
                    feedMe.getText("Press enter to continue");
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
