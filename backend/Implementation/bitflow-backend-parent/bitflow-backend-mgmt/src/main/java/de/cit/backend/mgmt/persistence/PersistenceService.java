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
import de.cit.backend.mgmt.persistence.model.PipelineStepDTO;
import de.cit.backend.mgmt.persistence.model.ProjectDTO;
import de.cit.backend.mgmt.persistence.model.UserDTO;
import de.cit.backend.mgmt.persistence.model.enums.AgentState;
import de.cit.backend.mgmt.persistence.model.enums.StepTypeEnum;

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
		String hql = "select user from UserDTO user where user.name = :name";
		Query query = entityManager.createQuery(hql);
		query.setParameter("name", username);
		
		List<UserDTO> results = query.getResultList();
		return results.size() != 1 ? null : results.get(0);
	}

	public List<UserDTO> findUsers(){
		String hqlQuery = "SELECT user FROM UserDTO user";
		Query query = entityManager.createQuery(hqlQuery);

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

	public List<CapabilityDTO> loadAvailableCapabilities() {
		String hqlQuery = "SELECT cap FROM CapabilityDTO cap";
		Query query = entityManager.createQuery(hqlQuery,CapabilityDTO.class);
		List<CapabilityDTO> results = query.getResultList();
		return results;
	}
	
	public CapabilityDTO findCapability(int capabilityId){
		return entityManager.find(CapabilityDTO.class, capabilityId);
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
	
	public CapabilityDTO findCapability(String name){
		String sqlQuery = "SELECT * FROM CAPABILITY WHERE name like ?";
		Query query = entityManager.createNativeQuery(sqlQuery,CapabilityDTO.class);
		query.setParameter(1, name);
		List<CapabilityDTO> results = query.getResultList();
		CapabilityDTO cap = results.size() != 0 ? results.get(0) : null;
		return cap;
	}
	
	public List<AgentDTO> filterAgents(PipelineDTO pipeline) {
		List<AgentDTO> agents = findAgentsByState(AgentState.ONLINE);
		return filterAgents(agents,pipeline);
	}
	
	public List<AgentDTO> filterAgents(List<AgentDTO> availableAgents,PipelineDTO pipeline) {
		CapabilityDTO capa;
		for(PipelineStepDTO step : pipeline.getPipelineSteps())
		{
			if (step.getType().equals(StepTypeEnum.OPERATION)) 
			{
				// improvable when Content of Pipeline Step contains Capability ID
				capa = this.findCapability(step.getContent());
				if (capa != null)
				{
					availableAgents.retainAll(capa.getAgents());
				}
			}
		}
		return availableAgents;
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

	public PipelineDTO findPipeline(String name) {
		String hql = "select pipeline from PipelineDTO pipeline where pipeline.name = :name";
		Query query = entityManager.createQuery(hql);
		query.setParameter("name", name);

		List<PipelineDTO> results = query.getResultList();
		return results.size() != 1 ? null : results.get(0);
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

	public void deletePipeline(PipelineDTO pipeline) {
		for(ProjectDTO pro : pipeline.getProjects()){
			pro.getPipelines().remove(pipeline);
		}
		
		pipeline.getProjects().clear();
		entityManager.remove(pipeline);
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
