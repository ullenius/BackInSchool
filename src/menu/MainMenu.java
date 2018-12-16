/**
 *
 * This is a helper class containing a CRUD-menu
 * as well as a SEARCH- and BONUS-submenus
 *
 * ALL methods are static
 *
 * @author Anosh D. Ullenius <anosh@anosh.se>
 * december 2018
 * 
 * Credits:
 * Thanks to: Lars J, Puya and our supervisor Bita J.
 * 
 */
package menu;

import database.Student;
import implementation.DaoImplementation;
import java.util.List;
import java.util.Scanner;
import util.InputHelper;

public final class MainMenu {
    
    private static final String WELCOME_MESSAGE = "Back in School"
            + " - a school management RDBMS."
            + "\nCreated by Anosh <anosh@anosh.se> (2018)\n\n";
    
    private static final String CREATE;
    private static final String READ;
    private static final String UPDATE; // NOT yet implemented
    private static final String DELETE;
    private static final String SEARCH;
    private static final String BONUS;
    private static final String EXIT;
    private static final String CREDITS;
    private static final String CREDITS_MESSAGE;
    
    private static final InputHelper feedMe;
    
    static {
        CREATE = "CREATE new entries";
        READ = "READ entries from database";
        UPDATE = "UPDATE entries from database";
        DELETE = "DELETE entries from database";
        SEARCH = "Searching in database (by id)";
        BONUS ="BONUS functionality";
        CREDITS = "Show CREDITS";
        CREDITS_MESSAGE = "Thanks to:\nLars J\nPuya\nand our supervisor Ms. Bita J.";
        EXIT = "Exit menu";
        
        feedMe = new InputHelper();
    }
    
    private MainMenu() { // exists only to defeat instantiation
    }
    
    public static void printMenu() {
        int userChoice = -1;
        
        System.out.println(WELCOME_MESSAGE);
        
        
        while (true) {
            System.out.println("MAIN MENU");
            
            System.out.println("1. " + CREATE);
            System.out.println("2. " + READ);
            System.out.println("3. " + UPDATE);
            System.out.println("4. " + DELETE);
            System.out.println("5. " + SEARCH);
            System.out.println("6. " + BONUS);
            System.out.println("7. " + CREDITS);
            System.out.println("8. " + EXIT);
            
            userChoice = feedMe.getInt("Please make your selection:");
            switch (userChoice) {
                
                case 1:
                    System.out.println(CREATE);
                    AddMenu.printMenu();
                    break;
                case 2:
                    System.out.println(READ);
                    ListAllMenu.printMenu();
                    break;
                case 3:
                    System.out.println(UPDATE);
                    UpdateMenu.printMenu();
                    break;
                case 4:
                    System.out.println(DELETE);
                    DeleteMenu.printMenu();
                    break;
                case 5:
                    System.out.println(SEARCH);
                    SearchMenu.printMenu();
                    break;
                case 6:
                    System.out.println(BONUS);
                    BonusMenu.printMenu();
                    break;
                case 7:
                    System.out.println(CREDITS);
                    System.out.println(WELCOME_MESSAGE);
                    System.out.println(CREDITS_MESSAGE);
                    feedMe.getText("Press enter to continue");
                    break;
                case 8:
                    System.out.println(EXIT);
                    System.out.println("Exiting menu");
                    return; // exiting method
                default:
                    System.out.println("Invalid selection");
                    break;
            }
        }
        
    }
    
    @Deprecated
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
