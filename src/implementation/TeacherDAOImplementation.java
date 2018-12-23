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
     * This methods deletes Teachers and does the necessary cleanup among the
     * courses they supervise.
     *
     * Obtains a List<Course> of results using native-query. Then proceeds
     * to iterate through the List in typical OOP-style (and set supervisor
     * id to null for all the Course objects in the list).
     *
     * @Throws TeacherNotFoundException if no matching Teacher is found
     * @param id 
     */
    @Override
    public void deleteTeacher(final int id) throws TeacherNotFoundException {

        em.getTransaction().begin();
        Query ariel = em.createNativeQuery("SELECT id,name,supervisor_id FROM "
                + "COURSE WHERE supervisor_id = ?id",Course.class);
        ariel.setParameter("id", id);
        List<Course> results = ariel.getResultList();
        
        Teacher teacher = em.find(Teacher.class, id);
        if (teacher == null)
            throw new TeacherNotFoundException("Teacher is not found");

        for (Course course : results) {
            course.setSupervisor(null);
            em.merge(course);
        }
        em.remove(teacher); // and finally, removes the Teacher
        em.getTransaction().commit();
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
                + "teacher.name = :new where teacher.id = :id");
        myQuery.setParameter("id", id);
        myQuery.setParameter("new", newName);
        int result = myQuery.executeUpdate(); // for debug purposes
        //System.out.println("rows affected = " + result);
        em.getTransaction().commit();
        
    }
}
