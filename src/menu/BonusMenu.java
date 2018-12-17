/*
* This is a helper class for miscellaneous data listings
* from the database. Mostly using native SQL queries.
* Did not fit within the other categories
* (and bonus sounds more fun than misc)
*
* ALL methods are static
*
*/
package menu;

/**
 *
 * @author Anosh D. Ullenius <anosh@anosh.se>
 */
public final class BonusMenu extends AbstractMenu {
    
    private static final String STUDENTS_COURSE;
    private static final String STUDENTS_EDUCATION;
    private static final String LONELY_TEACHERS;
    private static final String LONELY_STUDENTS;
    private static final String UNSUPERVISED_COURSES;
    
    static {
        STUDENTS_COURSE = "List all STUDENTS from a course";
        STUDENTS_EDUCATION = "List all STUDENTS from an education";
        UNSUPERVISED_COURSES = "List all courses *WITHOUT* a supervisor";
        LONELY_STUDENTS = "List STUDENTS not tied to an EDUCATION";
        LONELY_TEACHERS = "List TEACHERS who do not SUPERVISE any COURSES";
    }
    
    private BonusMenu() { // exits only to prevent instantiation
    }
    
    public static void printMenu() {
        
        int userChoice = -1;
        int targetID = 0;
        
        while (true) {
            System.out.println("BONUS SUBMENU");
            System.out.println("Bonus options");
            
            System.out.println("1. " + STUDENTS_COURSE);
            System.out.println("2. " + STUDENTS_EDUCATION);
            System.out.println("3. " + UNSUPERVISED_COURSES);
            System.out.println("4. " + LONELY_STUDENTS);
            System.out.println("5. " + LONELY_TEACHERS);
            System.out.println("6. " + EXIT);
            
            
            userChoice = feedMe.getInt("Please make your selection:");
            switch (userChoice) {
                
                case 1:
                    System.out.println(STUDENTS_COURSE);
                    targetID = feedMe.getInt("Enter COURSE id: ");
                    courseDAO.listStudentsInCourse(targetID).forEach(System.out::println);
                    feedMe.getText("Press enter to continue");
                    break;
                case 2:
                    System.out.println(STUDENTS_EDUCATION);
                    targetID = feedMe.getInt("Enter EDUCATION id: ");
                    educationDAO.findEducation(targetID).getGroupOfStudents().forEach(System.out::println);
                    feedMe.getText("Press enter to continue");
                    break;
                case 3:
                    System.out.println(UNSUPERVISED_COURSES);
                    System.out.println("Displaying all UNSUPERVISED courses");
                    courseDAO.listUnsupervisedCourses().forEach(System.out::println);
                    feedMe.getText("Press enter to continue");
                    break;
                case 4:
                    System.out.println(LONELY_STUDENTS);
                    studentDAO.listLonelyStudents().forEach(System.out::println);
                    feedMe.getText("Press enter to continue");
                    break;
                case 5:
                    System.out.println(LONELY_TEACHERS);
                    teacherDAO.listLonelyTeachers().forEach(System.out::println);
                    feedMe.getText("Press enter to continue");
                    break;
                case 6:
                    System.out.println("Exiting menu");
                    return; // exiting method
                default:
                    System.out.println("Invalid selection");
                    feedMe.getText("Press enter to continue");
                    break;
            }
        }
    }
    
}
