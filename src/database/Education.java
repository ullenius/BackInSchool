package database;


import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
/**
 *
 * @author Anosh D. Ullenius <anosh@anosh.se>
 */
@Entity
public class Education {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    private String name;
   
   @OneToMany
   private Set<Student> studentGroup;
   
   @ManyToMany
   private Set<Course> courseGroup;
   
   
   public Education() {
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
   
   public Set<Course> getCourseGroup() {
       return Collections.unmodifiableSet(courseGroup);
   }
    
}
