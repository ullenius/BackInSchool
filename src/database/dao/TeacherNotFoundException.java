package database.dao;

/**
 *
 * @author Anosh D. Ullenius <anosh@anosh.se>
 */
public class TeacherNotFoundException extends Exception {
    
   
    public TeacherNotFoundException(final String message) {
        super(message);
    }
    
    public TeacherNotFoundException() {
        
    }
    
}
