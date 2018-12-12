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
    private Long id;
    private String name;
   
   @OneToMany
   private Set<Student> students;
   
   @ManyToMany
   private Set<Course> courses;
   
   
   public Education() {
   }
   
   public Education(String name) {
       this.name = name;
       this.students = new HashSet<>();
       this.courses = new HashSet<>();
   }
   
   public void addStudent(Student studentToAdd) {
       students.add(studentToAdd);
   }
   
   public Set<Student> getStudents() {
       return Collections.unmodifiableSet(students);
   }
   
   public void addCourse(Course courseToAdd) {
       courses.add(courseToAdd);
   }
   
   public Set<Course> getCourses() {
       return Collections.unmodifiableSet(courses);
   }
    
}
