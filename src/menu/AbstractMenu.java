/*
* This class contains code used by all of the Menu-subclasses
* Design pattern DRY: - don't repeat yourself
*
* Separate what changes from what stays the same
*
*
*/
package menu;

import database.dao.CourseDAO;
import database.dao.EducationDAO;
import database.dao.StudentDAO;
import database.dao.TeacherDAO;
import implementation.CourseDAOImplementation;
import implementation.EducationDAOImplementation;
import implementation.StudentDAOImplementation;
import implementation.TeacherDAOImplementation;
import util.InputHelper;

/**
 *
 * @author Anosh D. Ullenius <anosh@anosh.se>
 */
abstract class AbstractMenu {
    
    static final CourseDAO courseDAO;
    static final EducationDAO educationDAO;
    static final TeacherDAO teacherDAO;
    static final StudentDAO studentDAO;
    
    static final String EXIT;
    static final InputHelper feedMe;
    
    static {
        EXIT = "Exit menu";
        feedMe = new InputHelper();
        
        // initialise the DAOs
        courseDAO = CourseDAOImplementation.getInstance();
        educationDAO = EducationDAOImplementation.getInstance();
        teacherDAO = TeacherDAOImplementation.getInstance();
        studentDAO = StudentDAOImplementation.getInstance();
    }
    
}
