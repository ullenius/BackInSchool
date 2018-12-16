/*
 * This is the DAO (data access object) used with Education object
 */
package database.dao;

import database.Education;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Anosh D. Ullenius <anosh@anosh.se>
 */
public interface EducationDAO {
    
    /**
     * 
     * CRUD for the Education objects themselves
     * @param newEducation 
     */
    
    public void addEducation(final Education newEducation);
    public void deleteEducation(final int id);
    public void updateEducationName(final String newName, final int id);
    public Education findEducation(final int id);
    public List<Education> listAllEducations();
    
    /**
     * ADD and DELETE from the list of Students
     * (one education contains many Students)
     * 
     * @param educationID
     * @param studentIdsToAdd 
     */
    public void addStudentsToEducation(final int educationID, final Set<Integer> studentIdsToAdd);
    public void removeStudentsFromEducation(final int educationID, final Set<Integer> studentIdsToRemove);
  
    /**
     * ADD and DELETE from the list of Courses 
     * (one education contains many courses)
     * 
     * @param educationID
     * @param courseIDsToAdd 
     */
    public void addCoursesToEducation(final int educationID, final Set<Integer> courseIDsToAdd);
    public void removeCoursesFromEducation(final int educationID, final Set<Integer> courseIDsToRemove);
    
}
