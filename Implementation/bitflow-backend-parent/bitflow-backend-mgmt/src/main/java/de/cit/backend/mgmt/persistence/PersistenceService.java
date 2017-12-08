package de.cit.backend.mgmt.persistence;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.cit.backend.persistence.model.Userdata;

@Stateless
public class PersistenceService {

	@PersistenceContext(unitName="bitflow-backend-mgmt-server")
	private EntityManager entityManager;
	
	@PostConstruct
	public void init(){
		System.out.println("PostConstruct - PersistenceService");
	}
	
	public Userdata findUser(int userId){
		return entityManager.find(Userdata.class, userId);
	}
}
