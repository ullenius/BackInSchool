package database;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Anosh D. Ullenius <anosh@anosh.se>
 */
@Entity
public class Student {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String name;
    
    public Student() {
    }
    
    public Student(String name) {
        this.name = name;
    }
    
}
