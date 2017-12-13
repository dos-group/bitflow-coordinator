package de.cit.backend.mgmt.persistence;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.BeforeClass;
import org.junit.Test;

import de.cit.backend.mgmt.persistence.model.AgentDTO;
import de.cit.backend.mgmt.persistence.model.AgentState;
import de.cit.backend.mgmt.persistence.model.ConfigurationDTO;
import de.cit.backend.mgmt.persistence.model.ProjectDTO;
import de.cit.backend.mgmt.persistence.model.UserDTO;

public class PersistenceAccessTest {

	private static EntityManager em;
	
	@BeforeClass
    public static void createInput() throws IOException {
		em = PersistenceManager.instance();
	}
	
	@Test
	public void findUserTest(){
		UserDTO user = em.find(UserDTO.class, 1);
		System.out.println(user.getCreatedProjects().size());
		System.out.println(user.getJoinedProjects().size());
		System.out.println(user.getEmail());
	}
	
	@Test
	public void findProjectTest(){
		ProjectDTO pro = em.find(ProjectDTO.class, 1);
		
		System.out.println(pro.getName());
		System.out.println(pro.getUserdata().getName());
		System.out.println(pro.getProjectMembers().size());
	}
	
	@Test
	public void findAgentTest(){
		String hqlQuery = "SELECT agent FROM AgentDTO agent WHERE agent.status = :status";
		Query query = em.createQuery(hqlQuery);
		query.setParameter("status", AgentState.ONLINE);
		
		List<AgentDTO> agentList = (List<AgentDTO>) query.getResultList();
		
		for(AgentDTO a : agentList){
			System.out.println(a.getIpAddress() + ":" + a.getPort());
		}
	}
	
	@Test
	public void getConfigTest(){
		String hqlQuery = "SELECT conf FROM ConfigurationDTO conf WHERE conf.configKey = :key";
		Query query = em.createQuery(hqlQuery);
		query.setParameter("key", ConfigurationService.CONFIG_MONITOR_INTERVAL);
		
		System.out.println(((ConfigurationDTO)query.getSingleResult()).getConfigValue());
	}
}
