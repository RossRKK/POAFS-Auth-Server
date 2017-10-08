package poafs.db.repo;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import poafs.db.Properties;

/**
 * A class that manages JPA entities.
 * @author rossrkk
 *
 * @param <Type> The type of the entity.
 */
public class Repository<Type> {
	
	/**
	 * The class of the entity.
	 */
	private Class<Type> typeClass;
	
	/**
	 * The local entity manager.
	 */
	private EntityManager entityManager;
	
	/**
	 * The entity manager factory.
	 */
	private EntityManagerFactory factory;
	
	/**
	 * Create a new Repository.
	 * @param typeClass The class of the type.
	 */
	public Repository(Class<Type> typeClass) {
		this.typeClass = typeClass;
		
		//Properties.setup();

        // This uses getPropertiesForTableValidation: to use the existing tables
        //Map<String, String> properties = Properties.getPropertiesForTableValidation();

        factory = Persistence.createEntityManagerFactory(typeClass.getSimpleName() + "Service");
        entityManager = factory.createEntityManager();

        factory.close();
	}
	
	/**
	 * Get an entity by id.
	 * @param id The id of the entity.
	 * @return The entity.
	 */
	public Type get(Object id) {
		return entityManager.find(typeClass, id);
	}
	
	/**
	 * Persist an entity.
	 * @param entity The entity to be saved.
	 */
	public void persist(Type entity) {
		entityManager.getTransaction().begin();
		
		entityManager.persist(entity);
		
		entityManager.getTransaction().commit();
	}
	
	/**
	 * Commit all changes.
	 */
	public void update() {
		entityManager.getTransaction().begin();

		entityManager.getTransaction().commit();
	}
	
	/**
	 * Close this repository.
	 */
	public void close() {
		// close down the entity manager and factory
		entityManager.close();
        factory.close();
	}
}
