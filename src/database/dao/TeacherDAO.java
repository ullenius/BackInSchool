/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.dao;

import database.Person;
import database.Teacher;
import java.util.List;

/**
 *
 * @author Anosh D. Ullenius <anosh@anosh.se>
 */
public interface TeacherDAO {
    
    public void deleteTeacher(final int id);
    public void addPerson(final Person personToAdd);
    public Teacher findTeacher(final int id);
    public List<Teacher> listAllTeachers();
    public List<Teacher> listLonelyTeachers();
    public void updateTeacherName(final String newName, final int id); 
     
}
