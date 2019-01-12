package database;

import database.dao.Persistable;
import java.io.Serializable;
import java.util.Objects;
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
public class Course implements Persistable, Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    private String name;
    
    @ManyToOne
    private Teacher supervisor;
    
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
    
    public String getSupervisorName() {
        return supervisor.getName();
}
    
    public String getName() {
        return name;
    }
    
    public void setName(String newName) {
        name = newName;
    }
    
    public int getId() {
        return id;
    }
    
    @Override
    public String toString() {
        String tutor;
        if (supervisor == null)
            tutor = "NO SUPERVISOR";
        else
            tutor = getSupervisorName();
        
        return id + " " + name + " " + tutor;
    }

    @Override
    public int hashCode() {
        return this.name.toLowerCase().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        return this.name.equalsIgnoreCase( ((Course) obj).getName());
    }
    
    
    
}
