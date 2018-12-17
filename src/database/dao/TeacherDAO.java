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
    
    public void deleteTeacher(int id);
    public void addPerson(Person personToAdd);
    @Deprecated
    public void addTeacher(Teacher teacherToAdd);
    public Teacher findTeacher(int id);
    public List<Teacher> listAllTeachers();
    public List<Teacher> listLonelyTeachers();
    public void updateTeacherName(String newName, int id); 
     
}
