package de.cit.backend.mgmt.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceManager {

	private static PersistenceManager pm;
	private EntityManager em;
	
	private PersistenceManager(){
		EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("bitflow-backend-mgmt");
    	em = emFactory.createEntityManager();
	}
	
	public static PersistenceManager instance(){
		if(pm == null){
			pm = new PersistenceManager();
		}
		return pm;
	}
}
