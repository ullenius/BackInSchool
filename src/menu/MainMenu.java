package menu;

import database.Student;
import implementation.DaoImplementation;
import java.util.List;
import java.util.Scanner;
import util.InputHelper;

/**
 *
 * This is a helper class containing a CRUD-menu
 * as well as a SEARCH- and BONUS-submenus
 *
 * @author Anosh D. Ullenius <anosh@anosh.se>
 */
public final class MainMenu {
    
    private static final String CREATE;
    private static final String READ;
    private static final String UPDATE; // NOT yet implemented
    private static final String DELETE;
    private static final String SEARCH;
    private static final String BONUS;
    private static final String EXIT;
    
    private static final DaoImplementation schoolDB;
    private static final InputHelper feedMe;
    
    static {
        CREATE = "CREATE new entries";
        READ = "READ entries from database";
        UPDATE = "UPDATE entries from database";
        DELETE = "DELETE entries from database";
        SEARCH = "Searching in database (by id)";
        BONUS ="BONUS functionality";
        EXIT = "Exit menu";
        
        feedMe = new InputHelper();
        schoolDB = DaoImplementation.getInstance();
    }
    
    private MainMenu() { //private constructor
    }
    
    public void printMenu() {
        int userChoice = -1;
        
        System.out.println("Adding submenu");
        System.out.println("Adding entries to database");
        
        System.out.println("1. " + CREATE);
        System.out.println("2. " + READ);
        System.out.println("3. " + UPDATE);
        System.out.println("4. " + DELETE);
        System.out.println("5. " + SEARCH);
        System.out.println("6. " + BONUS);
        System.out.println("7. " + EXIT);
        
        while (true) {
            userChoice = feedMe.getInt("Please make your selection:");
            switch (userChoice) {
                
                case 1:
                    System.out.println(CREATE);
                    break;
                case 2:
                    System.out.println(READ);
                    break;
                case 3:
                    System.out.println(UPDATE);
                    break;
                case 4:
                    System.out.println(DELETE);
                    break;
                case 5:
                    System.out.println(SEARCH);
                    break;
                case 6:
                    System.out.println(BONUS);
                    break;
                case 7:
                    System.out.println(EXIT);
                    System.out.println("Exiting menu");
                    return; // exiting method
                default:
                    System.out.println("Invalid selection");
                    break;
            }
        }
        
    }
    
    public static void main(String[] args) {
        
        statistics();
    }
    
    // Demo-code solely for testing functionality
    
    @Deprecated
    public static void statistics() {
        
        /**
         *
         * This method was used for testing purposes
         */
        
        Scanner sc = new Scanner(System.in);
        
        System.out.println("1. List students in course");
        System.out.println("Please enter course id");
        
        int choice = sc.nextInt();
        
        // singleton :)
        DaoImplementation schoolDB = DaoImplementation.getInstance(); 
        
        List<Student> results = schoolDB.listStudentsInCourse(choice);
        
        results.forEach(System.out::println);
        
        
    }
    
}
