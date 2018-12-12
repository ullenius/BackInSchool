package database;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * 3 konstruktorer,
 * 1. zero-argument (required by EE/Hibernate)
 * 2. en med String name
 * 3. en med String name, Teacher supervisor
 * 
 * 
 * @author Anosh D. Ullenius <anosh@anosh.se>
 */
@Entity
public class Course {
    
    @Id
     @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String name;
    
    @ManyToOne
    private Teacher supervisor;
    
    //2do, koppla ihop med Education
    
    public Course() {
        name = null;
        supervisor = null;
    }
    
    public Course(String name) {
        this.name = name;
        supervisor = null;
    }
    
    public Course(String name, Teacher supervisor) {
        this.name = name;
        this.supervisor = supervisor;
    }
    
    public void setSupervisor(Teacher newSupervisor) {
        supervisor = newSupervisor;
    }
    
    public Teacher getSupervisor() {
        return supervisor;
}
    
}
