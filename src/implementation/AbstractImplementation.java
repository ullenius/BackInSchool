/*
* This class contains methods used by all other DAO-implementation classes
* in this package.
*
* It also contains a shared EntityManager and EntityManager-factory
* used in this project
*
*/
package implementation;

import database.dao.Persistable;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Anosh D. Ullenius <anosh@anosh.se>
 */
public abstract class AbstractImplementation {
    
    static final EntityManagerFactory emf;
    static final EntityManager em;
    
    static {
        emf = Persistence.createEntityManagerFactory("BackInSchoolPU");
        em = emf.createEntityManager();
    }
    
    /**
     * Performs custom native SQL-query
     * Helper method used by other methods in this class
     *
     * Dangerous! Use with caution
     *
     * executeUpdate returns number of rows affected
     * if they are > 0 it was successfully executed
     *
     * @param customQuery
     */
    boolean customQuery(final String customQuery) {
        em.getTransaction().begin();
        
        Query myQuery = em.createNativeQuery(customQuery);
        int result = myQuery.executeUpdate();
        
        em.getTransaction().commit();
        
        return (result > 0);
    }
    /**
     *
     * This generic method is used to persist objects of Entity-class types
     * (the one I use in this project). Persistable is an empty marker
     * interface whose sole purpose is to distinguish them.
     *
     * @param <T>
     * @param entityToCommit
     */
    
    /**
     * This generic method is used to persist objects of Entity-class types
     * (the one I use in this project). Persistable is an empty marker
     * interface whose sole purpose is to distinguish them
     * @param <T>
     * @param entityToPersist
     */
    <T extends Persistable> void persistStuff(T entityToPersist) {
        
        em.getTransaction().begin();
        em.persist(entityToPersist);
        em.getTransaction().commit();
    }
    
    /**
     * 
     * This is a generic helper method used to return a class T of type
     * Persistable (marker interface) with a given id
     * 
     * Used to avoid code duplication in DAO implementation classes
     * 
     * @param <T>
     * @param id
     * @param entityClass
     * @return 
     */
    
    public <T extends Persistable> T findById(Class<T> entityClass, final int id) {
        
        em.getTransaction().begin();
        T result = em.find(entityClass,id);
        
        em.getTransaction().commit();
        return result;
    }
    
    /**
     * 
     * Method that closes the EntityManager
     * and the EntityManager Factory
     * 
     * Used for cleanup. Cannot be overriden
     * in subclasses
     */
    public final void closeEverything() {
    
    if (em.isOpen())
        em.close();

    if (emf.isOpen())
        emf.close();
}
    
        
}
