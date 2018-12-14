/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menu;

/**
 *
 * @author Anosh D. Ullenius <anosh@anosh.se>
 */
public class AddMenu {
    
    private static final String STUDENT;
    private static final String TEACHER;
    private static final String EDUCATION;
    private static final String COURSE;
    
    
    static {
        STUDENT = "Add student";
        TEACHER = "Add teacher";
        EDUCATION = "Add education";
        COURSE = "Add course";
    }
    
    public void printMenu() {
        
        System.out.println("Adding submenu");
        
        System.out.println("Adding entries to database");
        
        System.out.println("1. " + STUDENT);
        System.out.println("1. " + TEACHER);
        System.out.println("1. " + EDUCATION);
        
        
        
        
    }
    
    
    
}
