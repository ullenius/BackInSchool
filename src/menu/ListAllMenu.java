/*
* This is a helper class for displaying all entries as Lists (java.util.List)
* obtained from their respective database tables. That is:
*
* Listing all Students
* Listing all Teachers
* Listing all Courses
* Listing all Educations
*
* ALL methods are static
*
*/
package menu;

/**
 *
 * @author Anosh D. Ullenius <anosh@anosh.se>
 */
public final class ListAllMenu extends AbstractMenu {
    
    private static final String STUDENT;
    private static final String TEACHER;
    private static final String EDUCATION;
    private static final String COURSE;
    
    static {
        STUDENT = "Display all students";
        TEACHER = "Display all teachers";
        EDUCATION = "Display all educations";
        COURSE = "Display all courses";
    }
    
    private ListAllMenu() { // exists only to defeat instantiation
    }
    
    public static void printMenu() {
        
        int userChoice = -1;
        while (true) {
            System.out.println("LIST ALL SUBMENU");
            System.out.println("Display all entries from database");
            
            System.out.println("1. " + STUDENT);
            System.out.println("2. " + TEACHER);
            System.out.println("3. " + COURSE);
            System.out.println("4. " + EDUCATION);
            System.out.println("5. " + EXIT);
            
            userChoice = feedMe.getInt("Please make your selection:");
            switch (userChoice) {
                
                case 1:
                    System.out.println("Displaying all STUDENTS:");
                    studentDAO.listAllStudents().forEach(System.out::println);
                    break;
                case 2:
                    System.out.println("Displaying all TEACHERS:");
                    teacherDAO.listAllTeachers().forEach(System.out::println);
                    break;
                case 3:
                    System.out.println("Displaying all COURSES:");
                    courseDAO.listAllCourses().forEach(System.out::println);
                    break;
                case 4:
                    System.out.println("Displaying all EDUCATIONS:");
                    educationDAO.listAllEducations().forEach(System.out::println);
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
