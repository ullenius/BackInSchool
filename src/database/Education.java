/**
 *
 * This class contains 2 Sets, the get-methods
 * however return immutable (unmodifiable) Sets in order to
 * fully comply with the principles of object encapsulation.
 * 
 * @author Anosh D. Ullenius <anosh@anosh.se>
 */

package database;

import database.dao.Persistable;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Education implements Persistable, Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    private String name;
   
   @OneToMany
   private Set<Student> studentGroup;
   
   @ManyToMany
   private Set<Course> courseGroup;
   
   public Education() {
       this.studentGroup = new HashSet<>();
       this.courseGroup = new HashSet<>();
   }
   
   public Education(String name) {
       this.name = name;
       this.studentGroup = new HashSet<>();
       this.courseGroup = new HashSet<>();
   }
   
   public void addStudent(Student studentToAdd) {
       studentGroup.add(studentToAdd);
   }
   
   public Set<Student> getGroupOfStudents() {
       return Collections.unmodifiableSet(studentGroup);
   }
   
   public void addCourse(Course courseToAdd) {
       courseGroup.add(courseToAdd);
   }
   
   public void deleteCourse(Course courseToDelete) {
       courseGroup.remove(courseToDelete);
   }
   
   public Set<Course> getCourseGroup() {
       return Collections.unmodifiableSet(courseGroup);
   }
   
   public String getName() {
       return name;
   }
   
   @Override
   public String toString() {
       return id + " " + name;
   }
    
}
