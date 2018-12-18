/*
* Implementation of Teacher DAO
*/
package implementation;

import database.Person;
import database.Teacher;
import database.dao.TeacherDAO;
import java.util.List;
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
     * This method removes the Teacher with the given id
     * But first it sets all Supervisor foreign-key references
     * in the COURSE-table to NULL. Teacher.ID is the foreign key 
     * supervisor_id in COURSE.
     * 
     * It uses native SQL to do this using only one Query
     * 
     * Then it proceeds to delete the entry
     * 
     * Total number of queries executed: 2
     * 
     * @param id 
     */
    @Override
    public void deleteTeacher(int id) {
        em.getTransaction().begin();
        
        Query supervisorCleanup = em.createNativeQuery("UPDATE COURSE SET "
                + "SUPERVISOR_ID = NULL WHERE SUPERVISOR_ID = ?target;");
        supervisorCleanup.setParameter("target", id);
        supervisorCleanup.executeUpdate();
        
        Query myQuery = em.createNativeQuery("DELETE FROM TEACHER WHERE ID = ?target;");
        myQuery.setParameter("target", id);
        myQuery.executeUpdate();
        
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
    
    @Override
    public Teacher findTeacher(final int id) {
       
        return findById(Teacher.class,id);
    }
    
    @Override
    public List<Teacher> listAllTeachers() {
        final String sql = "SELECT id,name FROM TEACHER ORDER BY name;";
        
        em.getTransaction().begin();
        
        Query myQuery = em.createNativeQuery(sql,Teacher.class);
        List<Teacher> results = myQuery.getResultList();
        
        em.getTransaction().commit();
        return (results);
    }
    
    @Override
    public List<Teacher> listLonelyTeachers() {
        /**
         * This query lists all TEACHERS who do NOT supervise any COURSE
         * that is, *WITHOUT* ties to any COURSE
         */
        final String sql = "SELECT TEACHER.NAME,TEACHER.ID,COURSE.SUPERVISOR_ID " +
                "FROM TEACHER " +
                "LEFT OUTER JOIN COURSE " +
                "ON TEACHER.ID = COURSE.SUPERVISOR_ID " +
                "WHERE SUPERVISOR_ID IS NULL;";
        em.getTransaction().begin();
        
        Query myQuery = em.createNativeQuery(sql,Teacher.class);
        List<Teacher> results = myQuery.getResultList();
        
        em.getTransaction().commit();
        return (results);
    }
    
    @Override
    public void updateTeacherName(String newName, int id) {
        
        String sql = "UPDATE TEACHER SET NAME=\"";
        sql = sql.concat(newName+"\" WHERE ID = " + id);
        
        customQuery(sql);
    }
}
