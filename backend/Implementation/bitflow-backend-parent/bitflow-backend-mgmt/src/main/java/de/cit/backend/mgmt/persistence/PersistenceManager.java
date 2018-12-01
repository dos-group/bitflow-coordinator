package de.cit.backend.mgmt.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Use PersistenceService instead
 * 
 * @author Sven
 *
 */
@Deprecated
public class PersistenceManager {

	private static EntityManager em;
	
	private static EntityManager init(){
		EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("bitflow-backend-mgmt");
    	return emFactory.createEntityManager();
	}
	
	public static EntityManager instance(){
		if(em == null){
			em = init();
		}
		return em;
	}
}
