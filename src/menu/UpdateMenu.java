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
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import util.InputHelper;

/**
 *
 * @author Anosh D. Ullenius <anosh@anosh.se>
 */
public final class UpdateMenu {
    
    private UpdateMenu() { // exists only to defeat instantiation
        throw new AssertionError(); // this should never happen
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
        STUDENT = "Update student name";
        TEACHER = "Update teacher name";
        COURSE = "Update course name";
        
        SUPERVISOR = "Set a new supervisor";
        
        EDUCATION = "Update education name";
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
                    updateStudentName();
                    break;
                case 2:
                    System.out.println(TEACHER);
                    updateTeacherName();
                    break;
                case 3:
                    System.out.println(COURSE);
                    updateCourseName();
                    break;
                case 4:
                    System.out.println(SUPERVISOR);
                    updateSupervisor();
                    break;
                case 5:
                    System.out.println(EDUCATION);
                    updateEducationName();
                    break;
                case 6:
                    System.out.println(EDUCATION_ADD_STUDENTS);
                    addStudentsToEducation();
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
    
    /**
     * Updates the student name based on id
     */
    private static void updateStudentName() {
        
        int targetID = feedMe.getInt("Please enter student id");
        String newName = feedMe.getText("Please enter new name: ");
        System.out.println("Updating name in database for Student " + targetID);
        schoolDB.updateStudentName(newName, targetID);
    }
    
    private static void updateTeacherName() {
        
        int targetID = feedMe.getInt("Please enter TEACHER id");
        String newName = feedMe.getText("Please enter new name: ");
        System.out.println("Updating name in database for TEACHER " + targetID);
        schoolDB.updateTeacherName(newName, targetID);
    }
    
    private static void updateCourseName() {
        
        int targetID = feedMe.getInt("Please enter COURSE id");
        String newName = feedMe.getText("Please enter new name: ");
        System.out.println("Updating name in database for COURSE " + targetID);
        schoolDB.updateCourseName(newName, targetID);
    }
    
    private static void updateEducationName() {
        int targetID = feedMe.getInt("Please enter EDUCATION id");
        String newName = feedMe.getText("Please enter new name: ");
        System.out.println("Updating name in database for EDUCATION " + targetID);
        schoolDB.updateEducationName(newName, targetID);
        
    }
    
    private static void updateSupervisor() {
        
        int courseID = feedMe.getInt("Please enter COURSE id:");
        Integer supervisorID = feedMe.getInt("Please enter SUPERVISOR ID, -1 "
                + "to set it to NULL");
        if (supervisorID.intValue() == -1)
            supervisorID = null;
        schoolDB.updateSupervisor(courseID, supervisorID);
    }
    
    private static void addStudentsToEducation() {
        
        /**
         * 
         * This takes input from the user and puts it in an
         * int and a Set<Integer>
         */
        
        int educationID = feedMe.getInt("Please enter EDUCATION id:");
        int input = 0;
        System.out.println("Please enter student ID to add to EDUCATION");
        
        Set<Integer> studentsToAdd = new HashSet<>();
        while (input != -1) {
            input = feedMe.getInt("One entry per line, enter -1 to end");
            if (input != -1)
                studentsToAdd.add(input);
        }
 
        /**
         * Sends the education ID and a Set<Integer> to the method
         */
        
        schoolDB.addStudentsToEducation(educationID, studentsToAdd);
    }
    
    public void removeStudentsFromEducation() {
        
        
    }
            
    
}
