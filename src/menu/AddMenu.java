/*
*
* This is a helper class containing the ADD (CREATE) menu
* and its implementation
*
*/
package menu;

import database.Course;
import database.Education;
import database.Person;
import database.Student;
import database.Teacher;
import implementation.DaoImplementation;
import util.InputHelper;

/**
 *
 * @author Anosh D. Ullenius <anosh@anosh.se>
 */
public final class AddMenu {
    
    private AddMenu() { // private constructor
    }
    
    private static final String STUDENT;
    private static final String TEACHER;
    private static final String EDUCATION;
    private static final String COURSE;
    private static final String EXIT;
    
    private static final DaoImplementation schoolDB;
    private static final InputHelper feedMe;
    
    static {
        STUDENT = "Add student";
        TEACHER = "Add teacher";
        EDUCATION = "Add education";
        COURSE = "Add course";
        EXIT = "Exit menu";
        feedMe = new InputHelper();
        schoolDB = new DaoImplementation();
    }
    
    public void printMenu() {
        
        int userChoice = -1;
        
        System.out.println("Adding submenu");
        
        System.out.println("Adding entries to database");
        
        System.out.println("1. " + STUDENT);
        System.out.println("2. " + TEACHER);
        System.out.println("3. " + COURSE);
        System.out.println("4. " + EDUCATION);
        System.out.println("5. " + EXIT);
        
        while (true) {
            userChoice = feedMe.getInt("Please make your selection:");
            switch (userChoice) {
                
                case 1:
                    Person newStudent = makeStudent();
                    schoolDB.addPerson(newStudent);
                    break;
                case 2:
                    Person newTeacher = makeTeacher();
                    schoolDB.addPerson(newTeacher);
                    break;
                case 3:
                    Course newCourse = makeCourse();
                    schoolDB.addCourse(newCourse);
                    break;
                case 4:
                    Education newEducation = makeEducation();
                    schoolDB.addEducation(newEducation);
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
    
    private Student makeStudent() {
        
        System.out.println("Creating a new Student object");
        
        String name = feedMe.getText("Enter name");
        name = name.trim();
        Student newStudent = new Student(name);
        System.out.println("New Student object successfully created");
        
        return newStudent;
    }
    
    private Teacher makeTeacher() {
        
        System.out.println("Creating a new TEACHER object");
        
        String name = feedMe.getText("Enter name");
        name = name.trim();
        Teacher newTeacher = new Teacher(name);
        System.out.println("New TEACHER object successfully created");
        
        return newTeacher;
    }
    
    private Course makeCourse() {
        
        int supervisorID = -1;
        System.out.println("Creating a new COURSE object");
        String name = feedMe.getText("Enter name");
        name = name.trim();
        Course newCourse = new Course(name);
        supervisorID = feedMe.getInt("Enter supervisor (teacher id): ");
        
        Teacher supervisor = schoolDB.findTeacher(supervisorID);
        newCourse.setSupervisor(supervisor);
        System.out.println("New COURSE object successfully created");
        
        return newCourse;
    }
    
    private Education makeEducation() {

        System.out.println("Creating a new EDUCATION object");
        String name = feedMe.getText("Enter name");
        name = name.trim();
        Education newEducation = new Education(name);
        
        return newEducation;
    }
    
    
    
}
