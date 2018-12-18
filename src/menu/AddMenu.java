/*
*
* This is a helper class containing the ADD (CREATE) menu
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
import database.Teacher;
import java.util.Optional;

/**
 *
 * @author Anosh D. Ullenius <anosh@anosh.se>
 */
public final class AddMenu extends AbstractMenu {
    
    private AddMenu() { // exists only to prevent instantiation
    }
    
    private static final String STUDENT;
    private static final String TEACHER;
    private static final String EDUCATION;
    private static final String COURSE;
    
    static {
        STUDENT = "Add student";
        TEACHER = "Add teacher";
        EDUCATION = "Add education";
        COURSE = "Add course";
    }
    
    public static void printMenu() {
        
        int userChoice = -1;
        Person newStudent = null;
        Person newTeacher = null;
        Course newCourse = null;
        Education newEducation = null;
        
        while (true) {
        System.out.println("ADDING SUB-MENU");
        System.out.println("Adding entries to database");
        
        System.out.println("1. " + STUDENT);
        System.out.println("2. " + TEACHER);
        System.out.println("3. " + COURSE);
        System.out.println("4. " + EDUCATION);
        System.out.println("5. " + EXIT);
        
            userChoice = feedMe.getInt("Please make your selection:");
            switch (userChoice) {
                
                case 1:
                    newStudent = makeStudent();
                    studentDAO.addPerson(newStudent);
                    newStudent = null; // eligible for garbage collection
                    feedMe.getText("Press enter to continue");
                    break;
                case 2:
                    newTeacher = makeTeacher();
                    teacherDAO.addPerson(newTeacher);
                    newTeacher = null;
                    feedMe.getText("Press enter to continue");
                    break;
                case 3:
                    newCourse = makeCourse();
                    courseDAO.addCourse(newCourse);
                    newCourse = null;
                    feedMe.getText("Press enter to continue");
                    break;
                case 4:
                    newEducation = makeEducation();
                    educationDAO.addEducation(newEducation);
                    newEducation = null;
                    feedMe.getText("Press enter to continue");
                    break;
                case 5:
                    System.out.println("Exiting menu");
                    return; // exiting method
                default:
                    System.out.println("Invalid selection");
                    feedMe.getText("Press enter to continue");
                    break;
            }
        }
    }
    
    private static Student makeStudent() {
        
        System.out.println("Creating a new Student object");
        
        String name = feedMe.getText("Enter name");
        name = name.trim();
        Student newStudent = new Student(name);
        System.out.println("New Student object successfully created");
        
        return newStudent;
    }
    
    private static Teacher makeTeacher() {
        
        System.out.println("Creating a new TEACHER object");
        
        String name = feedMe.getText("Enter name");
        name = name.trim();
        Teacher newTeacher = new Teacher(name);
        System.out.println("New TEACHER object successfully created");
        
        return newTeacher;
    }
    
    private static Course makeCourse() {
        
        int supervisorID = -1;
        System.out.println("Creating a new COURSE object");
        String name = feedMe.getText("Enter name");
        name = name.trim();
        Course newCourse = new Course(name);
        supervisorID = feedMe.getInt("Enter supervisor (teacher id): ");
        
        /**
         * 
         * Include option to have no supervisor set? I.e. NULL
         */
        Optional<Teacher> supervisor = teacherDAO.findTeacher(supervisorID);
        if (supervisor.isPresent()) {
         newCourse.setSupervisor(supervisor.get());
        } else {
            System.out.println("No teacher found. Supervisor it set to NULL");
            newCourse.setSupervisor(null); // this works when persisting entity
        }
        
        System.out.println("New COURSE object successfully created");
        return newCourse;
    }
    
    private static Education makeEducation() {

        System.out.println("Creating a new EDUCATION object");
        String name = feedMe.getText("Enter name");
        name = name.trim();
        Education newEducation = new Education(name);
        
        return newEducation;
    }
    
}
