package database;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
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
    }
    
    public Course(String name) {
        this.name = name;
    }
    
    public void setSupervisor(Teacher newSupervisor) {
        supervisor = newSupervisor;
    }
    
    public Teacher getSupervisor() {
        return supervisor;
}
    
}
