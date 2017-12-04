package de.cit.backend.mgmt.persistence;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.cit.backend.persistence.model.Userdata;

@Stateless
public class PersistenceService {

	@PersistenceContext(unitName="bitflow-backend-mgmt")
	private EntityManager entityManager;
	
	public Userdata findUser(int userId){
		return entityManager.find(Userdata.class, userId);
	}
}
