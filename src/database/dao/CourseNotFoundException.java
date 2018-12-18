/*
*
 */
package database.dao;

/**
 *
 * @author Anosh D. Ullenius <anosh@anosh.se>
 */
public class CourseNotFoundException extends Exception {

    
    public CourseNotFoundException(final String message) {
        super(message);
    }
    
    public CourseNotFoundException() {
        
    }
    
}
