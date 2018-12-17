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
import implementation.CourseDAOImplementation;
import implementation.EducationDAOImplementation;
import implementation.StudentDAOImplementation;
import implementation.TeacherDAOImplementation;
import util.InputHelper;

/**
 *
 * @author Anosh D. Ullenius <anosh@anosh.se>
 */
public final class AddMenu {
    
    private AddMenu() { // exists only to prevent instantiation
    }
    
    private static final String STUDENT;
    private static final String TEACHER;
    private static final String EDUCATION;
    private static final String COURSE;
    private static final String EXIT;
    
    private static final CourseDAOImplementation courseDAO;
    private static final EducationDAOImplementation educationDAO;
    private static final TeacherDAOImplementation teacherDAO;
    private static final StudentDAOImplementation studentDAO;
    
    private static final InputHelper feedMe;
    
    static {
        STUDENT = "Add student";
        TEACHER = "Add teacher";
        EDUCATION = "Add education";
        COURSE = "Add course";
        EXIT = "Exit menu";
        feedMe = new InputHelper();
        
        // initialise the DAOs
        courseDAO = CourseDAOImplementation.getInstance();
        educationDAO = EducationDAOImplementation.getInstance();
        teacherDAO = TeacherDAOImplementation.getInstance();
        studentDAO = StudentDAOImplementation.getInstance();
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
                    feedMe.getText("Press enter to continue");
                    break;
                case 2:
                    newTeacher = makeTeacher();
                    teacherDAO.addPerson(newTeacher);
                    feedMe.getText("Press enter to continue");
                    break;
                case 3:
                    newCourse = makeCourse();
                    courseDAO.addCourse(newCourse);
                    feedMe.getText("Press enter to continue");
                    break;
                case 4:
                    newEducation = makeEducation();
                    educationDAO.addEducation(newEducation);
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
        
        Teacher supervisor = teacherDAO.findTeacher(supervisorID);
        newCourse.setSupervisor(supervisor);
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
