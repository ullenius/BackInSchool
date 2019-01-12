/*
* Implementation of Teacher DAO
*/
package implementation;

import database.Course;
import database.dao.TeacherNotFoundException;
import database.Person;
import database.Teacher;
import database.dao.TeacherDAO;
import java.util.List;
import java.util.Optional;
import javax.persistence.Query;

/**
 *
 * @author Anosh D. Ullenius <anosh@anosh.se>
 */
public class TeacherDAOImplementation extends AbstractImplementation implements TeacherDAO {
    
    private static TeacherDAOImplementation instance;
    
    static {
        instance = null; //singleton stuff. See getInstance()
    }
    
    private TeacherDAOImplementation() {
        // Exists only to defeat instantiation
    }
    
    public static TeacherDAOImplementation getInstance() {
        if (instance == null) {
            instance = new TeacherDAOImplementation();
        }
        return instance;
    }
    
    /**
     *
     * This methods deletes Teachers and does the necessary cleanup amongst the
     * courses they supervise.
     *
     * @Throws TeacherNotFoundException if no matching Teacher is found
     * @param id 
     */
    @Override
    public void deleteTeacher(final int id) throws TeacherNotFoundException {

        em.getTransaction().begin();
        // First, before we do anything. Check if the Teacher even exists
        Teacher teacher = em.find(Teacher.class, id);
        if (teacher == null) {
            em.getTransaction().rollback();
            throw new TeacherNotFoundException("Teacher was not found");
        }
        Query myQuery = em.createQuery("SELECT c FROM Course c WHERE c.supervisor = :value", Course.class);
        myQuery.setParameter("value", teacher);
        List<Course> results = myQuery.getResultList();
        
        for (Course course : results) {
            course.deleteSupervisor();
        }
        em.remove(teacher); // and finally, removes the Teacher
        em.getTransaction().commit(); // performs dirty checking and updates everything
    }
    
    @Override
    public void addPerson(Person personToAdd) {
       persistStuff(personToAdd);
    }
    
    @Deprecated
    // Deprecated by addPerson method
    @Override
    public void addTeacher(Teacher teacherToAdd) {
        persistStuff(teacherToAdd);
    }
    
    /**
     * 
     * Calls generic method implemented in superclass (AbstractImplementation)
     * @param id
     * @return 
     */
    @Override
    public Optional<Teacher> findTeacher(final int id) {
        
        return findEntity(Teacher.class,id);
    }
    
    @Override
    public List<Teacher> listAllTeachers() {
        final String sql = "SELECT id,name FROM TEACHER ORDER BY name;";
        return getResultList(Teacher.class,sql);
    }
    
        /**
         * This query lists all TEACHERS who do NOT supervise any COURSE
         * that is, *WITHOUT* ties to any COURSE
         * 
         * @return
         */
    @Override
    public List<Teacher> listLonelyTeachers() {
        final String sql = "SELECT TEACHER.NAME,TEACHER.ID,COURSE.SUPERVISOR_ID " +
                "FROM TEACHER " +
                "LEFT OUTER JOIN COURSE " +
                "ON TEACHER.ID = COURSE.SUPERVISOR_ID " +
                "WHERE SUPERVISOR_ID IS NULL;";
        return getResultList(Teacher.class,sql);
    }
    
    @Override
    /**
     * 
     * Uses JPQL in OOP-fashion. Treats Teacher as an object
     * rather than a table in a database
     */
    public void updateTeacherName(final String newName, final int id) {
        
        em.getTransaction().begin();
        Query myQuery = em.createQuery("UPDATE Teacher teacher SET "
                + "teacher.name = :new WHERE teacher.id = :id");
        myQuery.setParameter("id", id);
        myQuery.setParameter("new", newName);
        int result = myQuery.executeUpdate(); // for debug purposes
        //System.out.println("rows affected = " + result);
        em.getTransaction().commit();
        
    }
}
