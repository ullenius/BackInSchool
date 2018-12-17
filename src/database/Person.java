/**
 * 
 * Extends the DatabaseEntity interface since interfaces
 * cannot implement other interfaces
 * 
 * Note to self: is this needed?
 * 
 */
package database;

import database.dao.Persistable;

/**
 *
 * @author Anosh D. Ullenius <anosh@anosh.se>
 */

public interface Person extends Persistable {
    
    public String getName();
    
}
