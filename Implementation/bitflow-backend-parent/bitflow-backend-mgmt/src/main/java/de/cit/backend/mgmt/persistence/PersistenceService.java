package de.cit.backend.mgmt.persistence;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jboss.logging.Logger;

import de.cit.backend.mgmt.persistence.model.AgentDTO;
import de.cit.backend.mgmt.persistence.model.CapabilityDTO;
import de.cit.backend.mgmt.persistence.model.PipelineDTO;
import de.cit.backend.mgmt.persistence.model.ProjectDTO;
import de.cit.backend.mgmt.persistence.model.UserDTO;
import de.cit.backend.mgmt.persistence.model.enums.AgentState;

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

	public UserDTO findUser(String username){
		String sqlQuery = "SELECT * FROM USERDATA WHERE name=\""+username.replaceAll("\"","\\\"")+"\"";
		Query query = entityManager.createNativeQuery(sqlQuery, UserDTO.class);
		List<UserDTO> results = query.getResultList();
		UserDTO user = results.size() != 1 ? null : results.get(0);
		return user;
	}

	public List<UserDTO> findUsers(){
		String sqlQuery = "SELECT * FROM USERDATA";
		Query query = entityManager.createNativeQuery(sqlQuery, UserDTO.class);

		return (List<UserDTO>) query.getResultList();
	}	
	
	public List<AgentDTO> findAgents(){
		String sqlQuery = "SELECT * FROM AGENT";
		Query query = entityManager.createNativeQuery(sqlQuery, AgentDTO.class);

		return (List<AgentDTO>) query.getResultList();
	}

	public AgentDTO findAgent(int agentId) {
		return entityManager.find(AgentDTO.class, agentId);
	}
	
	public CapabilityDTO findCapability(CapabilityDTO capa){
		String sqlQuery = "SELECT * FROM CAPABILITY WHERE name=? and is_fork=? and description like ?";
		Query query = entityManager.createNativeQuery(sqlQuery,CapabilityDTO.class);
		query.setParameter(1, capa.getName());
		query.setParameter(2, capa.isIsFork());
		query.setParameter(3, capa.getDescription());
		List<CapabilityDTO> results = query.getResultList();
		CapabilityDTO cap = results.size() != 1 ? null : results.get(0);
		return cap;
	}
	
	public void deleteUser(int userId) {
		entityManager.remove(this.findUser(userId));	
	}

	public ProjectDTO findProject(int projectId) {
		return entityManager.find(ProjectDTO.class, projectId);
	}
	
	public PipelineDTO findPipeline(int pipelineId) {
		return entityManager.find(PipelineDTO.class, pipelineId);
	}

	@Deprecated
	public void deleteProject(int projectId) {
		deleteProject(findProject(projectId));
	}
	
	public void deleteProject(ProjectDTO project) {
		for(UserDTO user : project.getProjectMembers()){
			user.getJoinedProjects().remove(project);
		}
		for(PipelineDTO pipe : project.getPipelines()){
			pipe.getProjects().remove(project);
		}
		
		project.getProjectMembers().clear();
		project.getPipelines().clear();
		entityManager.remove(project);
	}

	public void deletePipeline(int pipelineId) {
		entityManager.remove(this.findPipeline(pipelineId));
	}

	public List<AgentDTO> findAgentsByState(AgentState state) {
		String hqlQuery = "SELECT agent FROM AgentDTO agent WHERE agent.status = :status";
		Query query = entityManager.createQuery(hqlQuery);
		query.setParameter("status", state);
		

		return (List<AgentDTO>) query.getResultList();
	}
	
	public void saveObject(Object object){
		entityManager.persist(object);
	}
	
	public void mergeObject(Object object){
		entityManager.merge(object);
	}
	
	public void flush(){
		entityManager.flush();
	}
}
