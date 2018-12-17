package util;
import java.util.Scanner;
/**
 *
 * Helper class with methods used for user I/O
 * using Scanner
 *
 * Based on code by Lars Jelleryd, 2018
 *
 * @author Modification Anosh. D Ullenius <anosh@anosh.se>
 *
 */
public final class InputHelper {
    
    private final Scanner sc;
    public InputHelper() {
        sc = new Scanner(System.in);
    }
    
    /**
     * Prints user defined message and returns a String
     * Uses StringBuilder to avoid putting stuff in the
     * String pool
     * 
     * @param message
     * @return 
     */
    public String getText(String message) {
        System.out.print(message);
        StringBuilder input = new StringBuilder(sc.nextLine());
        sc.nextLine(); // clears buffer
        return input.toString();
    }
    
    public int getInt(String message) {
        System.out.print(message);
        int input = sc.nextInt();
        sc.nextLine();
        
        return input;
    }
}
