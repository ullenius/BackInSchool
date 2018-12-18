/*
 * Implementation of the Education DAO (data access object) interface
 */
package implementation;

import database.Course;
import database.Education;
import database.Student;
import database.dao.EducationDAO;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.persistence.Query;

/**
 *
 * @author Anosh D. Ullenius <anosh@anosh.se>
 */
public class EducationDAOImplementation extends AbstractImplementation implements EducationDAO {
    
    private static EducationDAOImplementation instance;
    
    static {
        instance = null; //singleton stuff. See getInstance()
    }
    
    private EducationDAOImplementation() {
        // Exists only to defeat instantiation
    }
    
    public static EducationDAOImplementation getInstance() {
        if (instance == null) {
            instance = new EducationDAOImplementation();
        }
        return instance;
    }

    public void addEducation(final Education newEducation) {
        
        persistStuff(newEducation);
    }

    @Override
     public void deleteEducation(final int id) {
        
        em.getTransaction().begin();
        
        Education target = em.find(Education.class, id);
        if (target != null)
            em.remove(target);

        em.getTransaction().commit();
    }

    @Override
    public void updateEducationName(final String newName, final int id) {
        
        String sql = "UPDATE EDUCATION SET NAME=\"";
        sql = sql.concat(newName+"\" WHERE ID = " + id);
        
        customQuery(sql);
    }

    @Override
    public Education findEducation(final int id) {
        
        return findById(Education.class,id);
    }

    /**
     * 
     * Returns an ordered list of Educations
     * @return 
     */
    @Override
    public List<Education> listAllEducations() {
        final String sql = "SELECT id,name FROM EDUCATION ORDER BY name;";
        
        em.getTransaction().begin();
        
        Query myQuery = em.createNativeQuery(sql,Education.class);
        List<Education> results = myQuery.getResultList();
        
        em.getTransaction().commit();
        return (results);
    }

    /**
     * This method makes ONE SQL-query in order to ADD all entries at once
     * Uses Set for speed
     * Uses StringBuffer to avoid filling up the String-pool with junk Strings
     * 
     * BUGS: SQL only supports adding 1000 entries at once
     * 
     * @param educationID
     * @param studentIdsToAdd 
     */
    @Override
    public void addStudentsToEducation(final int educationID, 
            final Set<Integer> studentIdsToAdd) {
        
        StringBuffer sql = new StringBuffer("INSERT INTO EDUCATION_STUDENT"
                + " (Education_ID, studentGroup_ID) VALUES");
        
        Iterator myIterator = studentIdsToAdd.iterator();
        while (myIterator.hasNext()) {
            sql.append("("+ educationID + ","+myIterator.next()+"),");
        }
         // removes the last ',' character
        sql.deleteCharAt(sql.length()-1);
        
        customQuery(sql.toString()); // executes the Query
    }
    
    /**
     * 
     * This method makes ONE SQL-query in order to REMOVE all entries at once
     * @param educationID
     * @param studentIdsToRemove 
     */
    @Override
    public void removeStudentsFromEducation(final int educationID, 
            Set<Integer> studentIdsToRemove) {
        
        StringBuffer sql = new StringBuffer("DELETE FROM EDUCATION_STUDENT " +
        "WHERE Education_ID =" +educationID + " AND studentGroup_ID IN (");
        
        Iterator myIterator = studentIdsToRemove.iterator();
        while (myIterator.hasNext()) {
            sql.append(myIterator.next() + ",");
        }

        // replaces the last character ',' with an ending ')'
        sql.setCharAt(sql.length()-1, ')');
        
        customQuery(sql.toString()); // executes the query
    }

    /**
     * 
     * Please note that the SQL-query varies significantly from
     * the removeCoursesFromEducation-method even though at a glance
     * the methods may look identical.
     * 
     * The append loops also varies *significantly*
     * 
     * @param educationID
     * @param courseIDsToAdd 
     */
    @Override
    public void addCoursesToEducation(int educationID, Set<Integer> courseIDsToAdd) {
        
        StringBuffer sql = new StringBuffer("INSERT INTO EDUCATION_COURSE "
                + " (Education_ID, courseGroup_ID) VALUES");
        
        Iterator myIterator = courseIDsToAdd.iterator();
        while (myIterator.hasNext()) {
            sql.append("("+ educationID + ","+myIterator.next()+"),");
        }
         // removes the last ',' character
        sql.deleteCharAt(sql.length()-1);
        
        customQuery(sql.toString()); // executes the Query
    }

    /**
     * Please note that the SQL-query varies significantly from
     * the AddCoursesToEducation-method even though at a glance
     * the methods may look identical.
     * 
     * The append loops also varies *significantly*
     * 
     * @param educationID
     * @param courseIDsToRemove 
     */
    @Override
    public void removeCoursesFromEducation(final int educationID, 
            final Set<Integer> courseIDsToRemove) {
        
        StringBuffer sql = new StringBuffer("DELETE FROM EDUCATION_COURSE " +
        "WHERE Education_ID =" +educationID + " AND courseGroup_ID IN (");
        
        Iterator myIterator = courseIDsToRemove.iterator();
        while (myIterator.hasNext()) {
            sql.append(myIterator.next() + ",");
        }
        // replaces the last character ',' with an ending ')'
        sql.setCharAt(sql.length()-1, ')');
        
        customQuery(sql.toString()); // executes the query
    }
    
    /**
     * 
     * JPA caching is complete and utter rubbish. Otherwise this would be
     * unneccessary.
     * 
     * Thankfully native queries saves the day!
     * 
     * @param id
     * @return 
     */
    @Override
    public List<Course> listCoursesInEducation(int id) {
        
        final String sql = "SELECT COURSE.ID,COURSE.NAME,COURSE.SUPERVISOR_ID "
                + "FROM COURSE,EDUCATION_COURSE "
                + "WHERE COURSE.ID = EDUCATION_COURSE.coursegroup_id "
                + "AND EDUCATION_COURSE.education_id = ?target;";
        
        em.getTransaction().begin();
        
        Query myQuery = em.createNativeQuery(sql,Course.class);
        myQuery.setParameter("target", id);
        
        List<Course> results = myQuery.getResultList();
        
        em.getTransaction().commit();
        return (results);
    }

    @Override
    public List<Student> listStudentsInEducation(int id) {

        final String sql = "SELECT STUDENT.ID,STUDENT.NAME "
                + "FROM STUDENT,EDUCATION_STUDENT "
                + "WHERE STUDENT.ID = EDUCATION_STUDENT.studentgroup_id "
                + "AND EDUCATION_STUDENT.education_id = ?target;";
        
        em.getTransaction().begin();
        
        Query myQuery = em.createNativeQuery(sql,Student.class);
        myQuery.setParameter("target", id);
        
        List<Student> results = myQuery.getResultList();
        
        em.getTransaction().commit();
        return (results);
    }
    
}
