/*
*
*/
package database.dao;

/**
 *
 * @author Anosh D. Ullenius <anosh@anosh.se>
 */
public class StudentNotFoundException extends Exception {
    
    public StudentNotFoundException(final String message) {
        super(message);
    }
    
    public StudentNotFoundException() {
        
    }
}
