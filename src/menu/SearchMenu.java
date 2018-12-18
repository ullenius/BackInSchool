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
import java.util.Optional;

/**
 *
 * @author Anosh D. Ullenius <anosh@anosh.se>
 */
final class SearchMenu extends AbstractMenu {
    
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
        
        Optional<Student> foundStudent = studentDAO.findStudentById(id);
        String result = null;
        
        if (foundStudent.isPresent()) {
            result = "Found matching STUDENT: " + foundStudent.get().toString();
        } else {
            result = "Found no matching entry for " + id + " in database";
        }
        return result;
    }
    
    private static String findTeacherById(int id) {
        
        Optional<Teacher> foundTeacher = teacherDAO.findTeacher(id);
        String result = null;
        
        if (foundTeacher.isPresent()) {
            result = "Found matching TEACHER: " + foundTeacher.get().toString();
        } else {
            result = "Found no matching entry for " + id + " in database";
        }
        return result;
    }
    
    private static String findCourseById(int id) {
        Optional<Course> foundCourse = courseDAO.findCourse(id);
        String result = null;
        
        if (foundCourse.isPresent()) {
            result = "Found matching COURSE: " + foundCourse.get().toString();
        } else {
            result = "Found no matching entry for " + id + " in database";
        }
        return result;
    }
    
    private static String findEducationById(int id) {
        
        Optional<Education> foundEducation = educationDAO.findEducation(id);
        String result = null;
        
        if (foundEducation.isPresent()) {
            result = "Found matching EDUCATION: " + foundEducation.get().toString();
        } else {
            result = "Found no matching entry for " + id + " in database";
        }
        return result;
    }
    
}
