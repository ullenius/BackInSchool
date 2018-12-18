/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.dao;

/**
 *
 * @author Anosh D. Ullenius <anosh@anosh.se>
 */
public class EducationNotFoundException extends Exception {
    
    public EducationNotFoundException(final String message) {
        super(message);
    }
    
    public EducationNotFoundException() {
        
    }
    
    
}
