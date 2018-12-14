/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import database.Student;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Anosh D. Ullenius <anosh@anosh.se>
 */
public class MainMenu {

    public static void main(String[] args) {
        
        statistics();
    }
    
    
    
    
    public static void statistics() {
        
        Scanner sc = new Scanner(System.in);
        
        System.out.println("1. List students in course");
        System.out.println("Please enter course id");
        
        int choice = sc.nextInt();
        
        DaoImplementation schoolDB = new DaoImplementation(); // göra detta till en singleton
        
        List<Student> results = schoolDB.listStudentsInCourse(choice);
        
        results.forEach(System.out::println);
        
        
    }      
    
}
