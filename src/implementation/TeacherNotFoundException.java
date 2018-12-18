/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementation;

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
