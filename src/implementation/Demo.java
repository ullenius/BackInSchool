package implementation;

import database.Course;
import database.Education;
import database.Student;
import database.Teacher;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Anosh D. Ullenius <anosh@anosh.se>
 */
public class Demo {
    
    public static void main(String[] args) {
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("BackInSchoolPU");
        EntityManager em = emf.createEntityManager();
        
        em.getTransaction().begin();
        
        Student newStudent = new Student("Nisse");
        Education newEducation = new Education("Fullstack");
        Education secondEducation = new Education("Testare");
        
        Course newCourse = new Course("Java EE");
        Course secondCourse = new Course("Debugging");
        
        secondEducation.addCourse(secondCourse);
        secondEducation.addCourse(newCourse);
        newEducation.addCourse(newCourse);
        newEducation.addStudent(newStudent);
        
        em.persist(newStudent);
        em.persist(newEducation);
        em.persist(newCourse);
        em.persist(secondCourse);
        
//        Teacher newTeacher = new Teacher("Pythagoras");
//        
//        em.persist(newTeacher);
//        em.persist(myCourse);
        
        em.getTransaction().commit();
       
        
        
    }
}
