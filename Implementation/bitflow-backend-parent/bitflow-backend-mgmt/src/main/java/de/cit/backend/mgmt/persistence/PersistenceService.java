package de.cit.backend.mgmt.persistence;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jboss.logging.Logger;

import de.cit.backend.mgmt.persistence.model.AgentDTO;
import de.cit.backend.mgmt.persistence.model.ProjectDTO;
import de.cit.backend.mgmt.persistence.model.UserDTO;

@Stateless
public class PersistenceService {

	private static final Logger log = Logger.getLogger(PersistenceService.class);

	@PersistenceContext(unitName="bitflow-backend-mgmt-server")
	private EntityManager entityManager;
	
	@PostConstruct
	public void init(){
		log.info("EJB initialized");
	}
	
	public UserDTO findUser(int userId){
		return entityManager.find(UserDTO.class, userId);
	}
	
	public List<AgentDTO> findAgents(){
		String sqlQuery = "SELECT * FROM AGENT";
		Query query = entityManager.createNativeQuery(sqlQuery, AgentDTO.class);

		return (List<AgentDTO>) query.getResultList();
	}

	public void createUser(UserDTO user) {
		entityManager.persist(user);
	}

	public ProjectDTO findProject(int projectId) {
		return entityManager.find(ProjectDTO.class, projectId);
	}
}
