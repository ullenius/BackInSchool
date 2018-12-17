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
    
    public void addEducation(Education newEducation);
    public void deleteEducation(int id);
    public void updateEducationName(String newName, int id);
    public Education findEducation(int id);
    public List<Education> listAllEducations();
    
    /**
     * ADD and DELETE from the list of Students
     * (one education contains many Students)
     * 
     * @param educationID
     * @param studentIdsToAdd 
     */
    public void addStudentsToEducation(int educationID, Set<Integer> studentIdsToAdd);
    public void removeStudentsFromEducation(int educationID, Set<Integer> studentIdsToRemove);
  
    /**
     * ADD and DELETE from the list of Courses 
     * (one education contains many courses)
     * 
     * @param educationID
     * @param courseIDsToAdd 
     */
    public void addCoursesToEducation(int educationID, Set<Integer> courseIDsToAdd);
    public void removeCoursesFromEducation(int educationID, Set<Integer> courseIDsToRemove);
    
}
