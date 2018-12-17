package database;

import database.dao.Persistable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Anosh D. Ullenius <anosh@anosh.se>
 */
@Entity
public class Student implements Person, Persistable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    private String name;
    
    public Student() {
    }
    
    public Student(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public String toString() {
        return name + " (" +id +")";
    }
    
}
