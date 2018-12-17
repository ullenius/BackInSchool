/*
* This is a helper class containing menu and methods
* for SEARCHING entries in the database based on the id column
*
* ALL methods are static
*
*/
package menu;

import database.Course;
import database.Education;
import database.Student;
import database.Teacher;

/**
 *
 * @author Anosh D. Ullenius <anosh@anosh.se>
 */
public final class SearchMenu extends AbstractMenu {
    
    private static final String STUDENT;
    private static final String TEACHER;
    private static final String EDUCATION;
    private static final String COURSE;
    
    static {
        STUDENT = "Search for student";
        TEACHER = "Search for teacher";
        EDUCATION = "Sarch for education";
        COURSE = "Search for course";
    }
    
    private SearchMenu() { // exists only to defeat instantiation
    }
    
    public static void printMenu() {
        
        int userChoice = -1;
        int targetID = -1;
        
        while (true) {
        System.out.println("SEARCH BY ID SUBMENU");
        System.out.println("Searching for entries in the database");
        
        System.out.println("1. " + STUDENT);
        System.out.println("2. " + TEACHER);
        System.out.println("3. " + COURSE);
        System.out.println("4. " + EDUCATION);
        System.out.println("5. " + EXIT);
        
        
            userChoice = feedMe.getInt("Please make your selection:");
            switch (userChoice) {
                
                case 1:
                    System.out.println("Searching for STUDENT");
                    targetID = feedMe.getInt("Please enter id: ");
                    System.out.println(findStudentById(targetID));
                    break;
                case 2:
                    System.out.println("Searching for TEACHER");
                    targetID = feedMe.getInt("Please enter id: ");
                    System.out.println(findTeacherById(targetID));
                    break;
                case 3:
                    System.out.println("Searching for COURSE");
                    targetID = feedMe.getInt("Please enter id: ");
                    System.out.println(findCourseById(targetID));
                    break;
                case 4:
                    System.out.println("Searching for EDUCATION");
                    targetID = feedMe.getInt("Please enter id: ");
                    System.out.println(findEducationById(targetID));
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
    
    private static String findStudentById(int id) {
        
        Student foundStudent = studentDAO.findStudentById(id);
        
        /**
         * Default return message if search yields 0 results
         */
        String result = "Found no matching entry for " + id +
                " in database";
        
        if (foundStudent != null)
            result = "Found matching student: " + foundStudent.toString();
        
        return result;
    }
    
    private static String findTeacherById(int id) {
        
        Teacher foundTeacher = teacherDAO.findTeacher(id);
        
        /**
         * Default return message if search yields 0 results
         */
        String result = "Found no matching entry for " + id +
                " in database";
        
        if (foundTeacher != null)
            result = "Found matching TEACHER: " + foundTeacher.toString();
        
        return result;
    }
    
    private static String findCourseById(int id) {
        Course foundCourse = courseDAO.findCourse(id);
        
        /**
         * Default return message if search yields 0 results
         */
        String result = "Found no matching entry for " + id +
                " in database";
        
        if (foundCourse != null)
            result = "Found matching COURSE: " + foundCourse.toString();
        
        return result;
        
    }
    
    private static String findEducationById(int id) {
        Education foundEducation = educationDAO.findEducation(id);
        /**
         * Default return message if search yields 0 results
         */
        String result = "Found no matching entry for " + id +
                " in database";
        
        if (foundEducation != null)
            result = "Found matching COURSE: " + foundEducation.toString();
        
        return result;
    }
        
}
