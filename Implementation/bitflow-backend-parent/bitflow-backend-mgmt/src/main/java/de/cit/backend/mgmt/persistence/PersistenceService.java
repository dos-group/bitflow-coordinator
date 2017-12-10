package de.cit.backend.mgmt.persistence;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import de.cit.backend.mgmt.persistence.model.Agent;
import de.cit.backend.mgmt.persistence.model.Userdata;

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
	
	public List<Agent> findAgents(){
		String sqlQuery = "SELECT * FROM AGENT";
		Query query = entityManager.createNativeQuery(sqlQuery, Agent.class);

		return (List<Agent>) query.getResultList();
	}
}
