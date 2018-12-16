/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.dao;

import database.Education;
import java.util.List;

/**
 *
 * @author Anosh D. Ullenius <anosh@anosh.se>
 */
public interface EducationDAO {
    
    public void addEducation(final Education newEducation);
    public void deleteEducation(final int id);
    public Education findEducation(final int id);
    public List<Education> listAllEducations();
    public void updateEducationName(final String newName, final int id);
    
    
}
