/*
* This is a helper class containing the UPDATE menu
* and its implementation
*
* ALL methods are static
*
*/
package menu;

import database.dao.CourseNotFoundException;
import database.dao.EducationNotFoundException;
import database.dao.StudentNotFoundException;
import database.dao.TeacherNotFoundException;
import java.util.HashSet;
import java.util.Set;
/**
 *
 * @author Anosh D. Ullenius <anosh@anosh.se>
 */
public final class UpdateMenu extends AbstractMenu {
    
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
    }
    
    public static void printMenu() {
        
        int userChoice = -1;
        
        while (true) {
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
            
            userChoice = feedMe.getInt("Please make your selection:");
            switch (userChoice) {
                
                case 1:
                    System.out.println(STUDENT);
                    updateStudentName();
                    feedMe.getText("Press enter to continue");
                    break;
                case 2:
                    System.out.println(TEACHER);
                    updateTeacherName();
                    feedMe.getText("Press enter to continue");
                    break;
                case 3:
                    System.out.println(COURSE);
                    updateCourseName();
                    feedMe.getText("Press enter to continue");
                    break;
                case 4:
                    System.out.println(SUPERVISOR);
                    updateSupervisor();
                    feedMe.getText("Press enter to continue");
                    break;
                case 5:
                    System.out.println(EDUCATION);
                    updateEducationName();
                    feedMe.getText("Press enter to continue");
                    break;
                case 6:
                    System.out.println(EDUCATION_ADD_STUDENTS);
                    addStudentsToEducation();
                    feedMe.getText("Press enter to continue");
                    break;
                case 7:
                    System.out.println(EDUCATION_REMOVE_STUDENTS);
                    removeStudentsFromEducation();
                    feedMe.getText("Press enter to continue");
                    break;
                case 8:
                    System.out.println(EDUCATION_ADD_COURSES);
                    addCoursesToEducation();
                    feedMe.getText("Press enter to continue");
                    break;
                case 9:
                    removeCoursesFromEducation();
                    System.out.println(EDUCATION_REMOVE_COURSES);
                    feedMe.getText("Press enter to continue");
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
        studentDAO.updateStudentName(newName, targetID);
    }
    
    private static void updateTeacherName() {
        
        int targetID = feedMe.getInt("Please enter TEACHER id");
        String newName = feedMe.getText("Please enter new name: ");
        System.out.println("Updating name in database for TEACHER " + targetID);
        teacherDAO.updateTeacherName(newName, targetID);
    }
    
    private static void updateCourseName() {
        
        int targetID = feedMe.getInt("Please enter COURSE id");
        String newName = feedMe.getText("Please enter new name: ");
        System.out.println("Updating name in database for COURSE " + targetID);
        courseDAO.updateCourseName(newName, targetID);
    }
    
    private static void updateEducationName() {
        int targetID = feedMe.getInt("Please enter EDUCATION id");
        String newName = feedMe.getText("Please enter new name: ");
        System.out.println("Updating name in database for EDUCATION " + targetID);
        educationDAO.updateEducationName(newName, targetID);
        
    }
    
    private static void updateSupervisor() {
        
        int courseID = feedMe.getInt("Please enter COURSE id:");
        Integer supervisorID = feedMe.getInt("Please enter SUPERVISOR ID, -1 "
                + "to set it to NULL");
        if (supervisorID.intValue() == -1)
            supervisorID = null;
        try {
            courseDAO.updateSupervisor(courseID, supervisorID);
        } catch (CourseNotFoundException | TeacherNotFoundException e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to update supervisor!");
        }
    }
    
    private static void addStudentsToEducation() {
        
        int educationID = feedMe.getInt("Please enter EDUCATION id:");
        Set<Integer> studentsToAdd =
                makeSet("Please enter student IDs to ADD to EDUCATION");
        
        /**
         * Sends the education ID and a Set<Integer> to the method
         */
        try {
            educationDAO.addStudentsToEducation(educationID, studentsToAdd);
            System.out.println("Successfully added " + studentsToAdd.size()
                    + " students to education");
        } catch (EducationNotFoundException | StudentNotFoundException e) {
            System.out.println(e.getMessage());
            System.out.println("FAILED to add students to education");
        }
        
    }
    
    private static void removeStudentsFromEducation() {
        
        int educationID = feedMe.getInt("Please enter EDUCATION id:");
        Set<Integer> studentsToRemove =
                makeSet("Please enter student IDs to REMOVE to EDUCATION");
        
        /**
         * Sends the education ID and a Set<Integer> to the method
         */
        educationDAO.removeStudentsFromEducation(educationID, studentsToRemove);
        System.out.println("Successfully removed " + studentsToRemove.size()
                + " students to education");
    }
    /**
     *
     * This is a helper method used for interactive
     * user I/O.
     *
     * Takes input from the user and puts it in an
     * int and a Set<Integer>
     *
     */
    private static Set<Integer> makeSet(String message)  {
        
        int input = 0;
        Set<Integer> groupOfStudentIDs = new HashSet<>();
        System.out.println(message);
        
        while (input != -1) {
            input = feedMe.getInt("One entry per line, enter -1 to end");
            if (input != -1)
                groupOfStudentIDs.add(input);
        }
        return groupOfStudentIDs;
    }
    
    private static void addCoursesToEducation() {
        
        int educationID = feedMe.getInt("Please enter EDUCATION id:");
        Set<Integer> coursesToAdd =
                makeSet("Please enter COURSE IDs to ADD to EDUCATION");
        
        /**
         * Sends the education ID and a Set<Integer> to the method
         */
        educationDAO.addCoursesToEducation(educationID, coursesToAdd);
    }
    
    private static void removeCoursesFromEducation() {
        
        int educationID = feedMe.getInt("Please enter EDUCATION id:");
        Set<Integer> coursesToRemove =
                makeSet("Please enter COURSE IDs to REMOVE to EDUCATION");
        /**
         * Sends the education ID and a Set<Integer> to the method
         */
        educationDAO.removeCoursesFromEducation(educationID, coursesToRemove);
    }
    
}
