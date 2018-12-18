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
import java.util.List;
import java.util.Optional;
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
     * @throws RuntimeException back to caller method
     */
    boolean customQuery(final String customQuery) throws RuntimeException {
        em.getTransaction().begin();
        Query myQuery = em.createNativeQuery(customQuery);
        int result = myQuery.executeUpdate();
        em.getTransaction().commit();
        return (result > 0);
    }
    
    /**
     *
     * A generic helper method that performs a custom SQL SELECT statement and
     * returns a resultlist of type <T extends Persistable>
     *
     * @param <T>
     * @param entityClass
     * @param customQuery
     * @return
     */
    <T extends Persistable> List<T> getResultList(Class<T> entityClass,
            final String customQuery) {
        
        em.getTransaction().begin();
        Query myQuery = em.createNativeQuery(customQuery,entityClass);
        
        List<T> resultList = myQuery.getResultList();
        em.getTransaction().commit();
        return resultList;
    }
    
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
     * Finds a generic <T> Persistable (see interface) entity
     * and returns it as an Optional<T>
     * @param <T>
     * @param entityClass
     * @param id
     * @return
     */
    <T extends Persistable> Optional<T> findEntity(Class<T> entityClass, final int id) {
        
        em.getTransaction().begin();
        T result = em.find(entityClass, id);
        em.getTransaction().commit();
        
        if (result == null)
            return Optional.empty();
        else
            return Optional.of(result);
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
        
        if (em != null && em.isOpen())
            em.close();
        
        if (emf != null && emf.isOpen())
            emf.close();
    }
    
}
