package main;

import menu.MainMenu;

/**
 *
 * This class contains the Main method
 * This program does not support Threads
 *
 * Please start a separate instance of the program instead
 *
 *
 * @author Anosh D. Ullenius <anosh@anosh.se>
 */
public final class Main {
    
    private Main() {
    }
    
    public static void main(String[] args) {
        
        // Launches the program's main menu
        MainMenu.printMenu();
    }
    
}
