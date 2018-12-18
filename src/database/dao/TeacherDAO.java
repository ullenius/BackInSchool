package database.dao;

import database.Person;
import database.Teacher;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Anosh D. Ullenius <anosh@anosh.se>
 */
public interface TeacherDAO {
    
    public void deleteTeacher(int id) throws TeacherNotFoundException;
    public void addPerson(Person personToAdd);
    @Deprecated
    public void addTeacher(Teacher teacherToAdd);
    public Optional<Teacher> findTeacher(int id);
    public List<Teacher> listAllTeachers();
    public List<Teacher> listLonelyTeachers();
    public void updateTeacherName(String newName, int id); 
     
}
